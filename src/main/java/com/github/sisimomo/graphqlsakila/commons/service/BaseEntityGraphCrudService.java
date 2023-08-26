package com.github.sisimomo.graphqlsakila.commons.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseEntityGraphCrudService<EntityT, UidT, CreateDtoT, UpdateDtoT>
    extends BaseEntityGraphService<EntityT> {

  private CrudRepository<EntityT, ?> internalRepository;

  private BaseCruMapper<EntityT, ?, CreateDtoT, UpdateDtoT> internalMapper;

  /**
   * Find the entity by its Unique Identifier (UID), or throw an {@link UncheckedException} if it
   * doesn't exist.
   *
   * @param uid The Unique Identifier (UID) of the entity to be retrieved.
   * @param entityGraph The entityGraph to use for the query.
   * @return A single entity object.
   */
  protected abstract EntityT getByUid(@NotNull UidT uid, EntityGraph entityGraph) throws UncheckedException;

  /**
   * Find the entity by its Unique Identifier (UID), or throw an {@link UncheckedException} if it
   * doesn't exist.
   *
   * @param uid The Unique Identifier (UID) of the entity to be retrieved.
   * @param dynamicEntityGraph The on-the-fly generated EntityGraph to use for the query.
   * @return A single entity object.
   */
  public EntityT getByUid(@NotNull UidT uid, @NotNull DynamicEntityGraph dynamicEntityGraph) throws UncheckedException {
    return getByUid(uid, (EntityGraph) dynamicEntityGraph);
  }

  /**
   * Find the entity by its Unique Identifier (UID), or throw an {@link UncheckedException} if it
   * doesn't exist.
   *
   * @param uid The Unique Identifier (UID) of the entity to be retrieved.
   * @param entityGraphSuffix The entityGraph suffix to use for the query. Using
   *        {@link #getEntityGraphBySuffix}.
   * @return A single entity object.
   */
  public EntityT getByUid(@NotNull UidT uid, @NotBlank String entityGraphSuffix) throws UncheckedException {
    return getByUid(uid, getEntityGraphBySuffix(entityGraphSuffix));
  }

  /**
   * Find the entity by its Unique Identifier (UID), or throw an {@link UncheckedException} if it
   * doesn't exist. Using {@link #getByUid(UidT, EntityGraph)} by passing the Empty EntityGraph.
   *
   * @param uid The Unique Identifier (UID) of the entity to be retrieved.
   * @return A single entity object.
   */
  public EntityT getByUid(@NotNull UidT uid) throws UncheckedException {
    return getByUid(uid, EntityGraph.NOOP);
  }

  /**
   * Validates an UpdateDTO.
   *
   * @param updateDto The UpdateDTO to be validated.
   * @throws UncheckedException If the UpdateDTO is invalid.
   */
  protected void validateUpdateDto(@NotNull UpdateDtoT updateDto) throws UncheckedException {
    // Empty ready to be overridden.
  }

  /**
   * Update an entity using {@link BaseCruMapper#update} and update the corresponding entity in the
   * database.
   *
   * @param updateDto The UpdateDTO to be copied to the entity.
   * @param uid The Unique Identifier (UID) of the entity to be updated.
   */
  @Transactional
  public EntityT update(@NotNull UpdateDtoT updateDto, @NotNull UidT uid) {
    EntityT entity = getByUid(uid, getEntityGraphBySuffix(EntityGraphSuffix.WRITE));
    internalMapper.update(entity, updateDto);
    return save(entity);
  }

  /**
   * Validates a CreateDTO.
   *
   * @param createDto The CreateDTO to be validated.
   * @throws UncheckedException If the CreateDTO is invalid.
   */
  protected void validateCreateDto(@NotNull CreateDtoT createDto) throws UncheckedException {
    // Empty ready to be overridden.
  }

  /**
   * Converts a CreateDTO to an entity using {@link BaseCruMapper#convertToDao} and save it to the
   * database.
   *
   * @param createDto The CreateDTO to be converted to entity.
   */
  @Transactional
  public EntityT create(@NotNull CreateDtoT createDto) {
    validateCreateDto(createDto);
    EntityT entity = internalMapper.convertToDao(createDto);
    return save(entity);
  }

  /**
   * Converts a list of CreateDTOs to a list of entities using {@link BaseCruMapper#convertToDao} and
   * save it to the database
   *
   * @param createDtos The list of CreateDTOs to be converted to entities.
   */
  @Transactional
  public List<EntityT> createAll(@NotEmpty Collection<CreateDtoT> createDtos) throws UncheckedException {
    createDtos.forEach(this::validateCreateDto);
    Collection<EntityT> entities = createDtos.stream().map(internalMapper::convertToDao).collect(Collectors.toList());
    return saveAll(entities);
  }

  /**
   * Deletes an entity by its Unique Identifier (UID).
   *
   * @param uid The Unique Identifier (UID) of the entity to be deleted.
   * @throws UncheckedException No matching Unique Identifier (UID) found.
   */
  public abstract void deleteByUid(UidT uid) throws UncheckedException;

  /**
   * Save the provided entity.
   *
   * @param entity The entity object to save.
   */
  protected EntityT save(@NotNull EntityT entity) {
    return internalRepository.save(entity);
  }

  /**
   * Save all the provided entities.
   *
   * @param entities The entity objects to save.
   */
  protected List<EntityT> saveAll(@NotNull Collection<EntityT> entities) {
    return StreamSupport.stream(internalRepository.saveAll(entities).spliterator(), false).collect(Collectors.toList());
  }

}
