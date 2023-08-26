package com.github.sisimomo.graphqlsakila.business.datafetcher;

import com.github.sisimomo.graphqlsakila.business.service.StaffMemberService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StaffMemberMapper;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Payment;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rental;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMember;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberUpdate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Store;
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
public class StaffMemberDataFetcher {

  private final StaffMemberService service;

  private final StaffMemberMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "StaffMemberDataFetcher#byPaymentUuids")
  private BatchLoader<UUID, StaffMember> byPaymentUuids;

  @DgsDataLoader(name = "StaffMemberDataFetcher#byRentalUuids")
  private BatchLoader<UUID, StaffMember> byRentalUuids;

  @DgsDataLoader(name = "StaffMemberDataFetcher#byStoreUuids")
  private BatchLoader<UUID, StaffMember> byStoreUuids;

  @DgsQuery
  public Connection<StaffMember> staffMembersGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public StaffMember staffMemberGet(@InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public StaffMember staffMemberCreate(@InputArgument StaffMemberCreate staffMemberCreate) {
    return mapper.convertToDto(service.create(staffMemberCreate));
  }

  @DgsMutation
  public List<StaffMember> staffMemberCreateAll(@InputArgument List<StaffMemberCreate> staffMemberCreates) {
    return mapper.convertToDto(service.createAll(staffMemberCreates));
  }

  // TODO Create a way to Update staff Member password independently.

  // TODO Create a way to Update staff Member picture independently.

  @DgsMutation
  public StaffMember staffMemberUpdate(@InputArgument StaffMemberUpdate staffMemberUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(staffMemberUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.PAYMENT.TYPE_NAME, field = DgsConstants.PAYMENT.StaffMember)
  public CompletableFuture<StaffMember> loadPaymentStaff(DataFetchingEnvironment dfe) {
    Payment source = dfe.getSource();
    DataLoader<UUID, StaffMember> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byPaymentUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.RENTAL.TYPE_NAME, field = DgsConstants.RENTAL.StaffMember)
  public CompletableFuture<StaffMember> loadRentalStaff(DataFetchingEnvironment dfe) {
    Rental source = dfe.getSource();
    DataLoader<UUID, StaffMember> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byRentalUuids");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.STORE.TYPE_NAME, field = DgsConstants.STORE.Manager)
  public CompletableFuture<StaffMember> loadStoreStaff(DataFetchingEnvironment dfe) {
    Store source = dfe.getSource();
    DataLoader<UUID, StaffMember> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byStoreUuids");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    byPaymentUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getByPaymentUuids(uuids)));
    byRentalUuids =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByRentalUuids(uuids)));
    byStoreUuids = uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByStoreUuids(uuids)));
  }

}
