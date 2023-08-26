package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeatureRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.SpecialFeatureServiceError;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.SpecialFeatureBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.SpecialFeatureRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.SpecialFeatureDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.SpecialFeatureMapper;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SpecialFeatureService
    extends BaseEntityGraphUuidCrudService<SpecialFeatureEntity, SpecialFeatureRequest, SpecialFeatureRequest> {

  private final SpecialFeatureRepository repository;

  private final SpecialFeatureMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  SpecialFeatureService(SpecialFeatureRepository repository, SpecialFeatureMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.mapper = mapper;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<SpecialFeatureEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, SpecialFeatureDtoPathToDaoPath.class, pageRequest);
  }

  public List<List<SpecialFeatureEntity>> getAllByFilmUuids(List<UUID> uuids) {
    List<SpecialFeatureBatchLoaderProjection> projections = repository.findAllByFilmUuids(uuids);
    return uuids.stream().map(uuid -> projections.stream().filter(p -> p.getBatchLoaderUuidHelper().equals(uuid))
        .map(mapper::projectionToDao).toList()).toList();
  }

  public List<SpecialFeatureEntity> getAllByUids(@NotNull List<UUID> uuids) {
    return repository.findByUuidIn(uuids);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(SpecialFeatureServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
