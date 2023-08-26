package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CategoryRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.CategoryServiceError;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.CategoryMapper;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.CategoryBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.CategoryRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.CategoryDtoPathToDaoPath;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryService extends BaseEntityGraphUuidCrudService<CategoryEntity, CategoryRequest, CategoryRequest> {

  private final CategoryMapper mapper;

  private final CategoryRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  CategoryService(CategoryRepository repository, CategoryMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.mapper = mapper;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<CategoryEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, CategoryDtoPathToDaoPath.class, pageRequest);
  }

  public List<List<CategoryEntity>> getAllByFilmUuids(List<UUID> uuids) {
    List<CategoryBatchLoaderProjection> projections = repository.findAllByFilmUuids(uuids);
    return uuids.stream().map(uuid -> projections.stream().filter(p -> p.getBatchLoaderUuidHelper().equals(uuid))
        .map(mapper::projectionToDao).toList()).toList();
  }

  public List<CategoryEntity> getAllByUids(@NotNull List<UUID> uuids) {
    return repository.findByUuidIn(uuids);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(CategoryServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
