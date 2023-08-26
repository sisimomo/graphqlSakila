package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeature;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeatureRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.SpecialFeatureService;
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
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.SpecialFeatureMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class SpecialFeatureDataFetcher {

  private final SpecialFeatureService service;

  private final SpecialFeatureMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "SpecialFeatureDataFetcher#batchLoader")
  private BatchLoader<UUID, List<SpecialFeature>> batchLoader;

  @DgsQuery
  public Connection<SpecialFeature> specialFeaturesGet(DataFetchingEnvironment dfe,
      @InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public SpecialFeature specialFeatureGet(@InputArgument UUID uuid) {
    SpecialFeatureEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public SpecialFeature specialFeatureCreate(@InputArgument SpecialFeatureRequest specialFeatureCreate) {
    return mapper.convertToDto(service.create(specialFeatureCreate));
  }

  @DgsMutation
  public List<SpecialFeature> specialFeatureCreateAll(
      @InputArgument List<SpecialFeatureRequest> specialFeatureCreates) {
    return mapper.convertToDto(service.createAll(specialFeatureCreates));
  }

  @DgsMutation
  public SpecialFeature specialFeatureUpdate(@InputArgument SpecialFeatureRequest specialFeatureUpdate,
      @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(specialFeatureUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.SpecialFeatures)
  public CompletableFuture<List<SpecialFeature>> loadSpecialFeatures(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, List<SpecialFeature>> dataLoader =
        dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture
        .supplyAsync(() -> service.getAllByFilmUuids(uuids).stream().map(mapper::convertToDto).toList());
  }

}
