package com.github.sisimomo.graphqlsakila.customer.service;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CustomerDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.error.CountryServiceError;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CountryMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CountryRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.CountryRepository;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CountryService extends BaseEntityGraphUuidCrudService<CountryEntity, CountryRequest, CountryRequest> {

  private final CountryRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  CountryService(CountryRepository repository, CountryMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<CountryEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, CustomerDtoPathToDaoPath.class, pageRequest);
  }

  public List<CountryEntity> getByCityUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM country AS a RIGHT JOIN city AS b ON b.country_id = a.id", uuids, CountryEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(CountryServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
