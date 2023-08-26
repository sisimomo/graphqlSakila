package com.github.sisimomo.graphqlsakila.commons.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.SecretKey;

import org.hibernate.type.descriptor.java.spi.JdbcTypeRecommendationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.ScrollPosition.Direction;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sisimomo.graphqlsakila.commons.service.error.KeysetPaginationServiceError;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.commons.utils.AesGcmUtils;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CustomerDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollingDirection;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SortOrder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.ParseContextImpl;
import com.turkraft.springfilter.parser.node.FilterNode;

import graphql.relay.Connection;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeysetPaginationService {
  private final SecretKey key;
  private final ObjectMapper objectMapper;
  private final FilterParser filterParser;
  private final FilterSpecificationConverter filterSpecificationConverter;

  public KeysetPaginationService(@Value("${db.encryption.key}") String encodedKey, ObjectMapper objectMapper,
      FilterParser filterParser, FilterSpecificationConverter filterSpecificationConverter) {
    this.key = AesGcmUtils.stringToSecretKey(encodedKey);
    this.objectMapper = objectMapper;
    this.filterParser = filterParser;
    this.filterSpecificationConverter = filterSpecificationConverter;
  }

  /**
   * Converts a {@link KeysetScrollPosition} object to a JSON encrypted encoded Base64 String.
   *
   * @param position The {@link KeysetScrollPosition} object to be converted.
   * @return A JSON encrypted encoded Base64 String.
   */
  public String keysetScrollPositionToEncryptedString(KeysetScrollPosition position) {
    try {
      String serialized = objectMapper.writeValueAsString(position.getKeys());
      return new String(Base64.getEncoder().encode(AesGcmUtils.encrypt(serialized, key)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Converts JSON encrypted decoded Base64 String to a {@link KeysetScrollPosition} object.
   *
   * @param encryptedString The encrypted string that needs to be decrypted and converted to a
   *        {@link KeysetScrollPosition} object.
   * @param direction Specifies the direction of scrolling, either FORWARD or BACKWARD.
   * @return A {@link KeysetScrollPosition} object.
   */
  public KeysetScrollPosition encryptedStringToKeysetScrollPosition(String encryptedString,
      ScrollingDirection direction) {
    if (encryptedString == null) {
      return ScrollPosition.keyset();
    }
    try {
      String serialized = AesGcmUtils.decrypt(Base64.getDecoder().decode(encryptedString), key);
      TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
      return ScrollPosition.of(objectMapper.readValue(serialized, typeRef),
          direction.equals(ScrollingDirection.FORWARD) ? Direction.FORWARD : Direction.BACKWARD);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Throws an {@link UncheckedException}.
   *
   * @param fieldsFound The fields found.
   * @param notFoundField the name of the field that was not found.
   */
  private void throwNotFoundByDtoPathException(List<DtoPathToDaoPath> fieldsFound, String notFoundField)
      throws UncheckedException {
    throw new UncheckedException(KeysetPaginationServiceError.NOT_FOUND_BY_DTO_PATH, log::warn,
        fieldsFound.stream().map(DtoPathToDaoPath::getDtoPath).collect(Collectors.joining(".")) + "." + notFoundField);
  }

  /**
   * Find an enum element based on a given string value and enum class.
   *
   * @param value The value is a String representing a DTO path.
   * @param enumClass A Class object representing the enum type that implements the
   *        {@link DtoPathToDaoPath} interface. It is used to get the enum constants and perform
   *        filtering on them.
   * @return an enum constant that has a DTO path value that matches the input value parameter. If no
   *         matching enum constant is found, an empty Optional is returned.
   */
  private <DtoPathToDaoPathT extends DtoPathToDaoPath> Optional<DtoPathToDaoPathT> getEnumFromDtoPath(String value,
      Class<DtoPathToDaoPathT> enumClass) {
    return Stream.of(enumClass.getEnumConstants()).filter(e -> e.getDtoPath().equals(value)).findAny();
  }

  /**
   * Find the corresponding DAO path from the DTO path.
   *
   * @param dtoPath A string representing the path to a field in a DTO.
   * @param enumClass A Class object representing the enum type that implements the
   *        {@link DtoPathToDaoPath} interface. It is used to ensure type safety and to provide access
   *        to the enum constants that map the DTO paths to DAO paths.
   * @param reason Specifies the reason for calling the getDaoPath method. It can be either FILTER or
   *        SORT, indicating whether the method is being called for filtering or sorting purposes.
   * @return The method is returning a String which represents the DAO path obtained from the given
   *         DTO path and the provided enum class.
   * @throws UncheckedException If field doesn't allow sort/filter.
   */
  @SuppressWarnings("unchecked")
  private <DtoPathToDaoPathT extends DtoPathToDaoPath> String getDaoPath(final String dtoPath,
      final Class<DtoPathToDaoPathT> enumClass, DtoPathToDaoPathReason reason) throws UncheckedException {
    Class<DtoPathToDaoPathT> current = enumClass;
    List<DtoPathToDaoPath> fields = new ArrayList<>();
    for (String field : dtoPath.split(Pattern.quote("."))) {
      if (current == null) {
        throwNotFoundByDtoPathException(fields, field);
      }
      getEnumFromDtoPath(field, current).ifPresentOrElse(e -> {
        fields.add(e);
        if (reason.equals(DtoPathToDaoPathReason.FILTER) && !e.isFilterAllowed()) {
          throw new UncheckedException(KeysetPaginationServiceError.FIELD_DOES_NOT_ALLOW_FILTERING, log::warn, e,
              fields.stream().map(DtoPathToDaoPath::getDtoPath).collect(Collectors.joining(".")));
        }
        if (reason.equals(DtoPathToDaoPathReason.SORT) && !e.isSortAllowed()) {
          throw new UncheckedException(KeysetPaginationServiceError.FIELD_DOES_NOT_ALLOW_SORTING, log::warn, e,
              fields.stream().map(DtoPathToDaoPath::getDtoPath).collect(Collectors.joining(".")));
        }
      }, () -> throwNotFoundByDtoPathException(fields, field));
      current = (Class<DtoPathToDaoPathT>) fields.get(fields.size() - 1).getNested();
    }
    if (fields.get(fields.size() - 1).getNested() != null) {
      throw new UncheckedException(KeysetPaginationServiceError.BAD_FILTER, log::warn,
          fields.stream().map(DtoPathToDaoPath::getDtoPath).collect(Collectors.joining(".")));
    }
    return fields.stream().map(DtoPathToDaoPath::getDaoPath).collect(Collectors.joining("."));
  }

  /**
   * Converts a collection of user provided
   * {@link com.github.sisimomo.graphqlsakila.dgscodegen.types.Sort Sort} objects to a {@link Sort}
   * object.
   *
   * @param sortDto A collection of user provided Sort objects, which contain information about how to
   *        sort data.
   * @param dtoPathToDaoPathClass A Class object representing the enum type that implements the *
   *        {@link DtoPathToDaoPath} interface. It is used to map the field names in the sortDto to
   *        their corresponding field names in the DAO layer.
   * @return A {@link Sort} object.
   */
  private <DtoPathToDaoPathT extends DtoPathToDaoPath> Sort userSortToJpaSort(
      Collection<com.github.sisimomo.graphqlsakila.dgscodegen.types.Sort> sortDto,
      @NotNull Class<DtoPathToDaoPathT> dtoPathToDaoPathClass) {
    if (sortDto == null) {
      return Sort.unsorted();
    }
    return Sort.by(sortDto.stream()
        .map(s -> new Sort.Order(s.getOrder().equals(SortOrder.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC,
            getDaoPath(s.getField(), dtoPathToDaoPathClass, DtoPathToDaoPathReason.SORT)))
        .toList());
  }

  /**
   * Converts a string filter into a {@link Specification<EntityT>} object.
   *
   * @param filter The filter parameter is a string that represents filter conditions to be applied to
   *        a query.
   * @param dtoPathToDaoPathClass A Class object representing the enum type that implements the *
   *        {@link DtoPathToDaoPath} interface. It is used to map the field names in the sortDto to
   *        their corresponding field names in the DAO layer.
   * @return A {@link Specification<EntityT>} object.
   */
  private <DtoPathToDaoPathT extends DtoPathToDaoPath, EntityT> Specification<EntityT> stringFilterToSpecification(
      @Nullable String filter, @NotNull Class<DtoPathToDaoPathT> dtoPathToDaoPathClass) {
    if (filter == null) {
      return Specification.where(null);
    }
    FilterNode node = filterParser.parse(filter, new ParseContextImpl(
        dtoPath -> getDaoPath(dtoPath, dtoPathToDaoPathClass, DtoPathToDaoPathReason.FILTER), null));
    return filterSpecificationConverter.convert(node);
  }

  /**
   * Retrieves a {@link Window} of entities from a JPA repository based on specified filters, sorting,
   * and pagination parameters.
   *
   * @param repository A JPA repository that extends JpaSpecificationExecutor interface, which
   *        provides methods for querying the database using specifications.
   * @param dtoPathToDaoPathClass A Class object representing the enum type that implements the *
   *        {@link DtoPathToDaoPath} interface. It is used to map the field names in the sortDto to
   *        their corresponding field names in the DAO layer.
   * @param pageRequest pageRequest is an object that contains information about the pagination and
   *        sorting of the data to be retrieved. It includes the page size, current cursor position,
   *        sorting criteria, and any filters to be applied.
   * @return The returning {@link Window<EntityT>} object.
   */
  public <DtoPathToDaoPathT extends DtoPathToDaoPath, EntityT> Window<EntityT> getAll(
      JpaSpecificationExecutor<EntityT> repository, Class<DtoPathToDaoPathT> dtoPathToDaoPathClass,
      ScrollRequest pageRequest) {
    KeysetScrollPosition scrollPosition =
        encryptedStringToKeysetScrollPosition(pageRequest.getCursor(), pageRequest.getDirection());
    Specification<EntityT> spec = stringFilterToSpecification(pageRequest.getFilter(), dtoPathToDaoPathClass);
    return repository.findBy(spec, q -> {
      try {
        return q.limit(pageRequest.getSize())
            .sortBy(userSortToJpaSort(pageRequest.getSorts(), CustomerDtoPathToDaoPath.class)).scroll(scrollPosition);
      } catch (JdbcTypeRecommendationException e) {
        throw new UncheckedException(KeysetPaginationServiceError.BAD_FILTER, log::warn, e, pageRequest.getFilter());
      }
    });
  }

  /**
   * Converts the content of a {@link Window} object to a {@link List} of {@link Edge} objects.
   *
   * @param window The {@link Window} object to be converted.
   * @return A {@link List} of {@link Edge} objects.
   */
  private <T> List<Edge<T>> windowToEdges(Window<T> window) {
    return window.getContent().stream().map(item -> (Edge<T>) new DefaultEdge<>(item, new DefaultConnectionCursor(
        keysetScrollPositionToEncryptedString((KeysetScrollPosition) window.positionAt(item))))).toList();
  }

  /**
   * Converts a {@link Window} object to a {@link Connection} object with pagination information.
   * <p>
   * Be aware that {@link Connection#getPageInfo()}{@link graphql.relay.PageInfo#isHasPreviousPage()
   * .isHasPreviousPage()} will always be {@code false} because the {@link Window} API doesn't support
   * it.
   *
   * @param window The {@link Window} object to be converted.
   * @return A {@link Connection} object with pagination information.
   */
  public <T> Connection<T> windowToConnection(Window<T> window) {
    List<Edge<T>> edges = windowToEdges(window);
    PageInfo pageInfo;
    if (!edges.isEmpty()) {
      pageInfo = new DefaultPageInfo(edges.get(0).getCursor(), edges.get(edges.size() - 1).getCursor(), false,
          window.hasNext());
    } else {
      pageInfo = new DefaultPageInfo(null, null, false, false);
    }
    return new DefaultConnection<>(edges, pageInfo);
  }

  private enum DtoPathToDaoPathReason {
    FILTER,
    SORT
  }

}
