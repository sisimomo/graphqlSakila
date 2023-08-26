package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rating;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RatingRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.RatingService;
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
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.RatingMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class RatingDataFetcher {

  private final RatingService service;

  private final RatingMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "RatingDataFetcher#batchLoader")
  private BatchLoader<UUID, Rating> batchLoader;

  @DgsQuery
  public Connection<Rating> ratingsGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Rating ratingGet(@InputArgument UUID uuid) {
    RatingEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Rating ratingCreate(@InputArgument RatingRequest ratingCreate) {
    return mapper.convertToDto(service.create(ratingCreate));
  }

  @DgsMutation
  public List<Rating> ratingCreateAll(@InputArgument List<RatingRequest> ratingCreates) {
    return mapper.convertToDto(service.createAll(ratingCreates));
  }

  @DgsMutation
  public Rating ratingUpdate(@InputArgument RatingRequest ratingUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(ratingUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.Rating)
  public CompletableFuture<Rating> loadRating(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, Rating> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByFilmUuids(uuids)));
  }

}
