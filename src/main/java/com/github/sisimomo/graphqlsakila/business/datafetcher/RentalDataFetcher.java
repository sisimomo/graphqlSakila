package com.github.sisimomo.graphqlsakila.business.datafetcher;

import com.github.sisimomo.graphqlsakila.business.service.RentalService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.RentalMapper;
import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Payment;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rental;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RentalRequest;
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

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class RentalDataFetcher {

  private final RentalService service;

  private final RentalMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "RentalDataFetcher#batchLoader")
  private BatchLoader<UUID, Rental> batchLoader;

  @DgsQuery
  public Connection<Rental> rentalsGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Rental rentalGet(@InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public Rental rentalCreate(@InputArgument RentalRequest rentalCreate) {
    return mapper.convertToDto(service.create(rentalCreate));
  }

  @DgsMutation
  public List<Rental> rentalCreateAll(@InputArgument List<RentalRequest> rentalCreates) {
    return mapper.convertToDto(service.createAll(rentalCreates));
  }

  @DgsMutation
  public Rental rentalUpdate(@InputArgument RentalRequest rentalUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(rentalUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.PAYMENT.TYPE_NAME, field = DgsConstants.PAYMENT.Rental)
  public CompletableFuture<Rental> loadRentals(DataFetchingEnvironment dfe) {
    Payment source = dfe.getSource();
    DataLoader<UUID, Rental> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByPaymentUuids(uuids)));
  }

}
