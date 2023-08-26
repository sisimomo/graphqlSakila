package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopy;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopyCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rental;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmCopyService;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.FilmCopyMapper;
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
public class FilmCopyDataFetcher {

  private final FilmCopyService service;

  private final FilmCopyMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "FilmCopyDataFetcher#batchLoader")
  private BatchLoader<UUID, FilmCopy> batchLoader;

  @DgsQuery
  public Connection<FilmCopy> filmCopiesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public FilmCopy filmCopyGet(@InputArgument UUID uuid) {
    FilmCopyEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public FilmCopy filmCopyCreate(@InputArgument FilmCopyCreate filmCopyCreate) {
    return mapper.convertToDto(service.create(filmCopyCreate));
  }

  @DgsMutation
  public List<FilmCopy> filmCopyCreateAll(@InputArgument List<FilmCopyCreate> filmCopyCreates) {
    return mapper.convertToDto(service.createAll(filmCopyCreates));
  }

  @DgsData(parentType = DgsConstants.RENTAL.TYPE_NAME, field = DgsConstants.RENTAL.FilmCopy)
  public CompletableFuture<FilmCopy> loadFilmCopies(DataFetchingEnvironment dfe) {
    Rental source = dfe.getSource();
    DataLoader<UUID, FilmCopy> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllByRentalUuids(uuids)));
  }

}
