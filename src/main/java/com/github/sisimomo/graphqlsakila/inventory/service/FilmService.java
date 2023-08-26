package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.FilmServiceError;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.FilmRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.FilmDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.FilmMapper;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FilmService extends BaseEntityGraphUuidCrudService<FilmEntity, FilmRequest, FilmRequest> {

  private final FilmRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  FilmService(FilmRepository repository, FilmMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<FilmEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, FilmDtoPathToDaoPath.class, pageRequest);
  }

  public List<FilmEntity> getAllByFilmCopyUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM film AS a RIGHT JOIN inventory AS b ON b.film_id = a.id", uuids, FilmEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(FilmServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
