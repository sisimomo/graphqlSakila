package com.github.sisimomo.graphqlsakila.customer.service;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CustomerDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.error.CustomerServiceError;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CustomerMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerUpdate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.CustomerRepository;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService extends BaseEntityGraphUuidCrudService<CustomerEntity, CustomerCreate, CustomerUpdate> {

  private final CustomerRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  CustomerService(CustomerRepository repository, CustomerMapper mapper,
      KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<CustomerEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, CustomerDtoPathToDaoPath.class, pageRequest);
  }

  public List<CustomerEntity> getAllByPaymentUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM customer AS a RIGHT JOIN payment AS b ON b.customer_id = a.id", uuids, CustomerEntity.class);
  }

  public List<CustomerEntity> getAllByRentalUuids(List<UUID> uuids) {
    return repository.findByColumnInAndOrderByCase(
        "SELECT a.* FROM customer AS a RIGHT JOIN rental AS b ON b.customer_id = a.id", uuids, CustomerEntity.class);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(CustomerServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
