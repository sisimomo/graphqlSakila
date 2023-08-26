package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Actor;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ActorRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.ActorService;
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
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.ActorMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ActorDataFetcher {

  private final ActorService service;

  private final ActorMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "ActorDataFetcher#batchLoader")
  private BatchLoader<UUID, List<Actor>> batchLoader;

  @DgsQuery
  public Connection<Actor> actorsGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Actor actorGet(@InputArgument UUID uuid) {
    ActorEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Actor actorCreate(@InputArgument ActorRequest actorCreate) {
    return mapper.convertToDto(service.create(actorCreate));
  }

  @DgsMutation
  public List<Actor> actorCreateAll(@InputArgument List<ActorRequest> actorCreates) {
    return mapper.convertToDto(service.createAll(actorCreates));
  }

  @DgsMutation
  public Actor actorUpdate(@InputArgument ActorRequest actorUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(actorUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.Actors)
  public CompletableFuture<List<Actor>> loadActors(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, List<Actor>> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture
        .supplyAsync(() -> service.getAllByFilmUuids(uuids).stream().map(mapper::convertToDto).toList());
  }

}
