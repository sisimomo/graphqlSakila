package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RatingRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.RatingServiceError;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.RatingRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.RatingDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.RatingMapper;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RatingService extends BaseEntityGraphUuidCrudService<RatingEntity, RatingRequest, RatingRequest> {

  private final RatingRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  RatingService(RatingRepository repository, RatingMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<RatingEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, RatingDtoPathToDaoPath.class, pageRequest);
  }

  public List<RatingEntity> getAllByFilmUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase("SELECT a.* FROM rating AS a JOIN film AS b ON b.rating_id = a.id",
        "b.uuid", uuids, RatingEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(RatingServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
