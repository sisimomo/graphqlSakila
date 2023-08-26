package com.github.sisimomo.graphqlsakila.commons.service;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.IBaseUuidEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import java.util.UUID;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;

import jakarta.validation.constraints.NotNull;

public abstract class BaseEntityGraphUuidCrudService<EntityT extends IBaseUuidEntity, CreateDtoT, UpdateDtoT>
    extends BaseEntityGraphCrudService<EntityT, UUID, CreateDtoT, UpdateDtoT> {

  private final CrudUuidEntityGraphJpaRepository<EntityT, ?> internalCrudUuidRepository;

  protected BaseEntityGraphUuidCrudService(CrudUuidEntityGraphJpaRepository<EntityT, ?> internalCrudUuidRepository,
      BaseCruMapper<EntityT, ?, CreateDtoT, UpdateDtoT> internalMapper) {
    super(internalCrudUuidRepository, internalMapper);
    this.internalCrudUuidRepository = internalCrudUuidRepository;
  }

  @Override
  protected EntityT getByUid(@NotNull UUID uuid, EntityGraph entityGraph) throws UncheckedException {
    return internalCrudUuidRepository.findByUuid(uuid, entityGraph).orElseThrow(() -> notFoundByUuidException(uuid));
  }

  @Override
  public void deleteByUid(@NotNull UUID uuid) throws UncheckedException {
    if (internalCrudUuidRepository.deleteByUuid(uuid) == 0) {
      throw notFoundByUuidException(uuid);
    }
  }

  /**
   * If the UUID was not found, throw an exception.
   *
   * @param uuid The UUID of the entity that was not found.
   */
  protected abstract UncheckedException notFoundByUuidException(@NotNull UUID uuid);

}
