package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopy;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmService;
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
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.FilmMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class FilmDataFetcher {

  private final FilmService service;

  private final FilmMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "FilmDataFetcher#batchLoader")
  private BatchLoader<UUID, Film> batchLoader;

  @DgsQuery
  public Connection<Film> filmsGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Film filmGet(@InputArgument UUID uuid) {
    FilmEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Film filmCreate(@InputArgument FilmRequest filmCreate) {
    return mapper.convertToDto(service.create(filmCreate));
  }

  @DgsMutation
  public List<Film> filmCreateAll(@InputArgument List<FilmRequest> filmCreates) {
    return mapper.convertToDto(service.createAll(filmCreates));
  }

  @DgsMutation
  public Film filmUpdate(@InputArgument FilmRequest filmUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(filmUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILMCOPY.TYPE_NAME, field = DgsConstants.FILMCOPY.Film)
  public CompletableFuture<Film> loadFilms(DataFetchingEnvironment dfe) {
    FilmCopy source = dfe.getSource();
    DataLoader<UUID, Film> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByFilmCopyUuids(uuids)));
  }

}
