package com.github.sisimomo.graphqlsakila.inventory.datafetcher;

import com.github.sisimomo.graphqlsakila.commons.service.KeysetPaginationService;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Language;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.LanguageRequest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ScrollRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.LanguageService;
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
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.LanguageMapper;

import graphql.relay.Connection;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class LanguageDataFetcher {

  private final LanguageService service;

  private final LanguageMapper mapper;

  private final KeysetPaginationService keysetPaginationService;

  @DgsDataLoader(name = "LanguageDataFetcher#byFilmLanguage")
  private BatchLoader<UUID, Language> byFilmLanguage;

  @DgsDataLoader(name = "LanguageDataFetcher#byFilmOriginalLanguage")
  private BatchLoader<UUID, Language> byFilmOriginalLanguage;

  @DgsQuery
  public Connection<Language> languagesGet(@InputArgument ScrollRequest scrollRequest) {
    return keysetPaginationService.windowToConnection(service.getAll(scrollRequest).map(mapper::convertToDto));
  }

  @DgsQuery
  public Language languageGet(@InputArgument UUID uuid) {
    LanguageEntity entity = service.getByUid(uuid);
    return mapper.convertToDto(entity);
  }

  @DgsMutation
  public Language languageCreate(@InputArgument LanguageRequest languageCreate) {
    return mapper.convertToDto(service.create(languageCreate));
  }

  @DgsMutation
  public List<Language> languageCreateAll(@InputArgument List<LanguageRequest> languageCreates) {
    return mapper.convertToDto(service.createAll(languageCreates));
  }

  @DgsMutation
  public Language languageUpdate(@InputArgument LanguageRequest languageUpdate, @InputArgument UUID uuid) {
    return mapper.convertToDto(service.update(languageUpdate, uuid));
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.Language)
  public CompletableFuture<Language> loadLanguage(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, Language> dataLoader = dfe.getDataLoader(this.getClass().getSimpleName() + "#byFilmLanguage");
    return dataLoader.load(source.getUuid());
  }

  @DgsData(parentType = DgsConstants.FILM.TYPE_NAME, field = DgsConstants.FILM.OriginalLanguage)
  public CompletableFuture<Language> loadOriginalLanguage(DataFetchingEnvironment dfe) {
    Film source = dfe.getSource();
    DataLoader<UUID, Language> dataLoader =
        dfe.getDataLoader(this.getClass().getSimpleName() + "#byFilmOriginalLanguage");
    return dataLoader.load(source.getUuid());
  }

  @PostConstruct
  private void initBatchLoaders() {
    byFilmLanguage =
        uuids -> CompletableFuture.supplyAsync(() -> mapper.convertToDto(service.getAllLanguagesByFilmUuids(uuids)));
    byFilmOriginalLanguage = uuids -> CompletableFuture
        .supplyAsync(() -> mapper.convertToDto(service.getAllOriginalLanguagesByFilmUuids(uuids)));
  }

}
