package com.github.sisimomo.graphqlsakila.customer.datafetcher;

import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Customer;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMember;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Store;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataLoader;
import com.github.sisimomo.graphqlsakila.customer.service.AddressService;
import com.github.sisimomo.graphqlsakila.customer.service.dto.AddressWithId;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.AddressMapper;

import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class AddressDataFetcher {

  private final AddressService service;

  private final AddressMapper mapper;

  @DgsDataLoader(name = "AddressDataFetcher#byCustomerUuids")
  private BatchLoader<UUID, AddressWithId> byCustomerUuids;

  @DgsDataLoader(name = "AddressDataFetcher#byStaffMemberUuids")
  private BatchLoader<UUID, AddressWithId> byStaffMemberUuids;

  @DgsDataLoader(name = "AddressDataFetcher#byStoreUuids")
  private BatchLoader<UUID, AddressWithId> byStoreUuids;

  @DgsData(parentType = DgsConstants.CUSTOMER.TYPE_NAME, field = DgsConstants.CUSTOMER.Address)
  public CompletableFuture<AddressWithId> loadCustomer(DataFetchingEnvironment dfe) {
    Customer source = dfe.getSource();
    DataLoader<UUID, AddressWithId> dataLoader =
        dfe.getDataLoader(this.getClass().getSimpleName() + "#byCustomerUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.STAFFMEMBER.TYPE_NAME, field = DgsConstants.STAFFMEMBER.Address)
  public CompletableFuture<AddressWithId> loadStaff(DataFetchingEnvironment dfe) {
    StaffMember source = dfe.getSource();
    DataLoader<UUID, AddressWithId> dataLoader =
        dfe.getDataLoader(this.getClass().getSimpleName() + "#byStaffMemberUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.STORE.TYPE_NAME, field = DgsConstants.STORE.Address)
  public CompletableFuture<AddressWithId> loadStore(DataFetchingEnvironment dfe) {
    Store source = dfe.getSource();
    DataLoader<UUID, AddressWithId> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byStoreUuids");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    byCustomerUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByCustomerUuids(uuids)));
    byStaffMemberUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByStaffMemberUuids(uuids)));
    byStoreUuids = uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByStoreUuids(uuids)));
  }

}
