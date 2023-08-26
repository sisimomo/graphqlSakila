package com.github.sisimomo.graphqlsakila.business.service;

import com.github.sisimomo.graphqlsakila.business.service.error.PaymentServiceError;
import com.github.sisimomo.graphqlsakila.business.service.mapper.PaymentMapper;
import java.util.UUID;

import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.PaymentEntity;
import com.github.sisimomo.graphqlsakila.business.dao.repository.PaymentRepository;
import com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath.PaymentDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.commons.service.BaseEntityGraphUuidCrudService;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentUpdate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService extends BaseEntityGraphUuidCrudService<PaymentEntity, PaymentCreate, PaymentUpdate> {

  private final PaymentRepository repository;

  private final KeysetPaginationService keysetPaginationService;

  PaymentService(PaymentRepository repository, PaymentMapper mapper, KeysetPaginationService keysetPaginationService) {
    super(repository, mapper);
    this.repository = repository;
    this.keysetPaginationService = keysetPaginationService;
  }

  public Window<PaymentEntity> getAll(ScrollRequest pageRequest) {
    return keysetPaginationService.getAll(repository, PaymentDtoPathToDaoPath.class, pageRequest);
  }

  @Override
  protected UncheckedException notFoundByUuidException(@NotNull UUID uuid) {
    return new UncheckedException(PaymentServiceError.NOT_FOUND_BY_UUID, log::warn, uuid);
  }

}
