package com.github.sisimomo.graphqlsakila.customer.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.City;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CityRequest;
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
import com.github.sisimomo.graphqlsakila.customer.service.CityService;
import com.github.sisimomo.graphqlsakila.customer.service.dto.AddressWithId;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CityMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CityDataFetcher {

  private final CityService service;

  private final CityMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "CityDataFetcher#batchLoader")
  private BatchLoader<Long, City> batchLoader;

  @DgsQuery
  public Connection<City> citiesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public City cityGet(DataFetchingEnvironment dfe, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public City cityCreate(@InputArgument CityRequest cityCreate) {
    return mapper.convertToDto(service.create(cityCreate));
  }

  @DgsMutation
  public List<City> cityCreateAll(@InputArgument List<CityRequest> cityCreates) {
    return mapper.convertToDto(service.createAll(cityCreates));
  }

  @DgsMutation
  public City cityUpdate(@InputArgument CityRequest cityUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(cityUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.ADDRESS.TYPE_NAME, field = DgsConstants.ADDRESS.City)
  public CompletableFuture<City> load(DataFetchingEnvironment dfe) {
    AddressWithId source = dfe.getSource();
    DataLoader<Long, City> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getId());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = ids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByAddressIds(ids)));
  }

}
