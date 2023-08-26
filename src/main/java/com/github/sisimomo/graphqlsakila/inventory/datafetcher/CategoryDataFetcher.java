package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Category;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CategoryRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.CategoryService;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.CategoryMapper;
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
public class CategoryDataFetcher {

  private final CategoryService service;

  private final CategoryMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "CategoryDataFetcher#batchLoader")
  private BatchLoader<UUID, List<Category>> batchLoader;

  @DgsQuery
  public Connection<Category> categoriesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Category categoryGet(@InputArgument UUID uuid) {
    CategoryEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Category categoryCreate(@InputArgument CategoryRequest categoryCreate) {
    return mapper.convertToDto(service.create(categoryCreate));
  }

  @DgsMutation
  public List<Category> categoryCreateAll(@InputArgument List<CategoryRequest> categoryCreates) {
    return mapper.convertToDto(service.createAll(categoryCreates));
  }

  @DgsMutation
  public Category categoryUpdate(@InputArgument CategoryRequest categoryUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(categoryUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.Categories)
  public CompletableFuture<List<Category>> loadCategory(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, List<Category>> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#batchLoader");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    batchLoader = uuids -> CompletableFuture
        .supplyAsync(() -> service.getAllByFilmUuids(uuids).stream().map(mapper::convertToDto).toList());
  }

}
