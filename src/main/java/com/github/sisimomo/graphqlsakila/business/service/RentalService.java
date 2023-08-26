package com.github.sisimomo.graphqlsakila.business.service;

import com.github.sisimomo.graphqlsakila.business.service.error.RentalServiceError;
import com.github.sisimomo.graphqlsakila.business.service.mapper.RentalMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RentalRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.repository.RentalRepository;
import com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath.RentalDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RentalService extends BaseEntityGraphUuidCrudService<RentalEntity, RentalRequest, RentalRequest> {

  private final RentalRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  RentalService(RentalRepository repository, RentalMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<RentalEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, RentalDtoPathToDaoPath.class, pageRequest);
  }

  public List<RentalEntity> getAllByPaymentUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM rental AS a RIGHT JOIN payment AS b ON b.rental_id = a.id", uuids, RentalEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(RentalServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
