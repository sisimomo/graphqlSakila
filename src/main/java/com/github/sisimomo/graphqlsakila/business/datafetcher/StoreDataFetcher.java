package com.github.sisimomo.graphqlsakila.business.datafetcher;

import com.github.sisimomo.graphqlsakila.business.service.StoreService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StoreMapper;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Customer;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopy;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMember;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Store;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StoreRequest;
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

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class StoreDataFetcher {

  private final StoreService service;

  private final StoreMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "StoreDataFetcher#byCustomerUuids")
  private BatchLoader<UUID, Store> byCustomerUuids;

  @DgsDataLoader(name = "StoreDataFetcher#byStaffMemberUuids")
  private BatchLoader<UUID, Store> byStaffMemberUuids;

  @DgsDataLoader(name = "StoreDataFetcher#byFilmCopyUuids")
  private BatchLoader<UUID, Store> byFilmCopyUuids;

  @DgsQuery
  public Connection<Store> storesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Store storeGet(@InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public Store storeCreate(@InputArgument StoreRequest storeCreate) {
    return mapper.convertToDto(service.create(storeCreate));
  }

  @DgsMutation
  public List<Store> storeCreateAll(@InputArgument List<StoreRequest> storeCreates) {
    return mapper.convertToDto(service.createAll(storeCreates));
  }

  @DgsMutation
  public Store storeUpdate(@InputArgument StoreRequest storeUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(storeUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.CUSTOMER.TYPE_NAME, field = DgsConstants.CUSTOMER.Store)
  public CompletableFuture<Store> loadCustomer(DataFetchingEnvironment dfe) {
    Customer source = dfe.getSource();
    DataLoader<UUID, Store> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byCustomerUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.STAFFMEMBER.TYPE_NAME, field = DgsConstants.STAFFMEMBER.Store)
  public CompletableFuture<Store> loadStaff(DataFetchingEnvironment dfe) {
    StaffMember source = dfe.getSource();
    DataLoader<UUID, Store> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byStaffMemberUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.FILMCOPY.TYPE_NAME, field = DgsConstants.FILMCOPY.Store)
  public CompletableFuture<Store> loadFilmCopy(DataFetchingEnvironment dfe) {
    FilmCopy source = dfe.getSource();
    DataLoader<UUID, Store> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byFilmCopyUuids");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    byCustomerUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByCustomerUuids(uuids)));
    byStaffMemberUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByStaffMemberUuids(uuids)));
    byFilmCopyUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByFilmCopyUuids(uuids)));
  }

}
