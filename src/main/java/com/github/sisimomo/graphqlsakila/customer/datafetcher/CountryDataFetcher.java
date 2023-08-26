package com.github.sisimomo.graphqlsakila.customer.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.City;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Country;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CountryRequest;
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
import com.github.sisimomo.graphqlsakila.customer.service.CountryService;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CountryMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class CountryDataFetcher {

  private final CountryService service;

  private final CountryMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "CountryDataFetcher#batchLoader")
  private BatchLoader<UUID, Country> batchLoader;

  @DgsQuery
  public Connection<Country> countriesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Country countryGet(@InputArgument UUID uuid) {
    return mapper.convertToDto(service.getByUid(uuid));
  }

  @DgsMutation
  public Country countryCreate(@InputArgument CountryRequest countryCreate) {
    return mapper.convertToDto(service.create(countryCreate));
  }

  @DgsMutation
  public List<Country> countryCreateAll(@InputArgument List<CountryRequest> countryCreates) {
    return service.createAll(countryCreates).stream().map(mapper::convertToDto).toList();
  }

  @DgsMutation
  public Country countryUpdate(@InputArgument CountryRequest countryUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(countryUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.CITY.TYPE_NAME, field = DgsConstants.CITY.Country)
  public CompletableFuture<Country> load(DataFetchingEnvironment dfe) {
    City source = dfe.getSource();
    DataLoader<UUID, Country> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture
        .supplyAsync(() -> service.getByCityUuids(uuids).stream().map(mapper::convertToDto).toList());
  }

}
