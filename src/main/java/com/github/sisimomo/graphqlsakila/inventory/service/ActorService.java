package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ActorRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.ActorServiceError;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.ActorBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.ActorRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.ActorDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.ActorMapper;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActorService extends BaseEntityGraphUuidCrudService<ActorEntity, ActorRequest, ActorRequest> {

  private final ActorRepository repository;

  private final ActorMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  ActorService(ActorRepository repository, ActorMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.mapper = mapper;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<ActorEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, ActorDtoPathToDaoPath.class, pageRequest);
  }

  public List<List<ActorEntity>> getAllByFilmUuids(List<UUID> uuids) {
    List<ActorBatchLoaderProjection> projections = repository.findAllByFilmUuids(uuids);
    return uuids.stream().map(uuid -> projections.stream().filter(p -> p.getBatchLoaderUuidHelper().equals(uuid))
        .map(mapper::projectionToDao).toList()).toList();
  }

  public List<ActorEntity> getAllByUids(@NotNull List<UUID> uuids) {
    return repository.findByUuidIn(uuids);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(ActorServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
