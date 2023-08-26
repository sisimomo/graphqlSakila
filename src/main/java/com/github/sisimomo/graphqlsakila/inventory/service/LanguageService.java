package com.github.sisimomo.graphqlsakila.inventory.service;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.LanguageRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.service.error.LanguageServiceError;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.repository.LanguageRepository;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.LanguageDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.LanguageMapper;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LanguageService extends BaseEntityGraphUuidCrudService<LanguageEntity, LanguageRequest, LanguageRequest> {

  private final LanguageRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  LanguageService(LanguageRepository repository, LanguageMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<LanguageEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, LanguageDtoPathToDaoPath.class, pageRequest);
  }

  public List<LanguageEntity> getAllLanguagesByFilmUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM language AS a JOIN film AS b ON b.language_id = a.id", uuids, LanguageEntity.class);
  }

  public List<LanguageEntity> getAllOriginalLanguagesByFilmUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM language AS a RIGHT JOIN film AS b ON b.original_language_id = a.id", uuids,
        LanguageEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(LanguageServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
