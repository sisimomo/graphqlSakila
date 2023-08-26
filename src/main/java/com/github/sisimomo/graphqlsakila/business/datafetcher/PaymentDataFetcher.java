package com.github.sisimomo.graphqlsakila.business.datafetcher;

import com.github.sisimomo.graphqlsakila.business.service.PaymentService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.PaymentMapper;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Payment;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentUpdate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import java.util.List;
import java.util.UUID;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import graphql.relay.Connection;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class PaymentDataFetcher {

  private final PaymentService service;

  private final PaymentMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsQuery
  public Connection<Payment> paymentsGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Payment paymentGet(@InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public Payment paymentCreate(@InputArgument PaymentCreate paymentCreate) {
    return mapper.convertToDto(service.create(paymentCreate));
  }

  @DgsMutation
  public List<Payment> paymentCreateAll(@InputArgument List<PaymentCreate> paymentCreates) {
    return mapper.convertToDto(service.createAll(paymentCreates));
  }

  @DgsMutation
  public Payment paymentUpdate(@InputArgument PaymentUpdate paymentUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(paymentUpdate, uuid));
  }

}
