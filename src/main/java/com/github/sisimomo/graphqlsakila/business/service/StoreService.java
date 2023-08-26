package com.github.sisimomo.graphqlsakila.business.service;

import com.github.sisimomo.graphqlsakila.business.service.error.StoreServiceError;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StoreMapper;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.dao.repository.StoreRepository;
import com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath.StoreDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StoreRequest;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoreService extends BaseEntityGraphUuidCrudService<StoreEntity, StoreRequest, StoreRequest> {

  private final StoreRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  StoreService(StoreRepository repository, StoreMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<StoreEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, StoreDtoPathToDaoPath.class, pageRequest);
  }

  public List<StoreEntity> getAllByCustomerUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM store AS a RIGHT JOIN customer AS b ON b.store_id = a.id", uuids, StoreEntity.class);
  }

  public List<StoreEntity> getAllByStaffMemberUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM store AS a RIGHT JOIN staff AS b ON b.store_id = a.id", uuids, StoreEntity.class);
  }

  public List<StoreEntity> getAllByFilmCopyUuids(@NotNull List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM store AS a RIGHT JOIN inventory AS b ON b.store_id = a.id", uuids, StoreEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(StoreServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
