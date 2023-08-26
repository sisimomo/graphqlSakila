package com.github.sisimomo.graphqlsakila.customer.service;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CityDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.error.CityServiceError;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CityMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CityRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CityEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.CityRepository;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CityService extends BaseEntityGraphUuidCrudService<CityEntity, CityRequest, CityRequest> {

  private final CityRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  CityService(CityRepository repository, CityMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<CityEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, CityDtoPathToDaoPath.class, pageRequest);
  }

  public List<CityEntity> getAllByAddressIds(List<Long> ids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM city AS a RIGHT JOIN address AS b ON b.city_id = a.id", "b.id", ids, CityEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(CityServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
