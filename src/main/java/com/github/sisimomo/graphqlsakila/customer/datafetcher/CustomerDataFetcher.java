package com.github.sisimomo.graphqlsakila.customer.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Customer;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerUpdate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Payment;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rental;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataLoader;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CustomerService;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CustomerMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CustomerDataFetcher {

  private final CustomerService service;

  private final CustomerMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "CustomerDataFetcher#byPaymentUuids")
  private BatchLoader<UUID, Customer> byPaymentUuids;

  @DgsDataLoader(name = "CustomerDataFetcher#byRentalUuids")
  private BatchLoader<UUID, Customer> byRentalUuids;

  @DgsQuery
  public Connection<Customer> customersGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Customer customerGet(@InputArgument UUID uuid) {
    CustomerEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Customer customerCreate(@InputArgument CustomerCreate customerCreate) {
    return mapper.convertToDto(service.create(customerCreate));
  }

  @DgsMutation
  public List<Customer> customerCreateAll(@InputArgument List<CustomerCreate> customerCreates) {
    return mapper.convertToDto(service.createAll(customerCreates));
  }

  @DgsMutation
  public Customer customerUpdate(@InputArgument CustomerUpdate customerUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(customerUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.PAYMENT.TYPE_NAME, field = DgsConstants.PAYMENT.Customer)
  public CompletableFuture<Customer> loadPayment(DataFetchingEnvironment dfe) {
    Payment source = dfe.getSource();
    DataLoader<UUID, Customer> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byPaymentUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.RENTAL.TYPE_NAME, field = DgsConstants.RENTAL.Customer)
  public CompletableFuture<Customer> loadRental(DataFetchingEnvironment dfe) {
    Rental source = dfe.getSource();
    DataLoader<UUID, Customer> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byRentalUuids");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    byPaymentUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByPaymentUuids(uuids)));
    byRentalUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByRentalUuids(uuids)));
  }

}
