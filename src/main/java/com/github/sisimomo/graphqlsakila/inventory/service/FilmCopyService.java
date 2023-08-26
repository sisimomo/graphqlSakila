package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopyCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.FilmCopyServiceError;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.FilmCopyMapper;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.FilmCopyRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.FilmCopyDtoPathToDaoPath;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FilmCopyService extends BaseEntityGraphUuidCrudService<FilmCopyEntity, FilmCopyCreate, Void> {

  private final FilmCopyRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  FilmCopyService(FilmCopyRepository repository, FilmCopyMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<FilmCopyEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, FilmCopyDtoPathToDaoPath.class, pageRequest);
  }

  public List<FilmCopyEntity> getAllByRentalUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM inventory AS a RIGHT JOIN rental AS b ON b.inventory_id = a.id", uuids, FilmCopyEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(FilmCopyServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
