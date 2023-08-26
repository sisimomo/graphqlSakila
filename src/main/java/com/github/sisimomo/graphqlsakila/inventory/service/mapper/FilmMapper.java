package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Film;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.ActorService;
import com.github.sisimomo.graphqlsakila.inventory.service.CategoryService;
import com.github.sisimomo.graphqlsakila.inventory.service.LanguageService;
import com.github.sisimomo.graphqlsakila.inventory.service.RatingService;
import com.github.sisimomo.graphqlsakila.inventory.service.SpecialFeatureService;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {ActorMapper.class, CategoryMapper.class,
    LanguageMapper.class, RatingMapper.class, SpecialFeatureMapper.class})
public abstract class FilmMapper implements BaseCruMapper<FilmEntity, Film, FilmRequest, FilmRequest> {

  @Autowired
  protected ActorService actorService;

  @Autowired
  protected CategoryService categoryService;

  @Autowired
  protected LanguageService languageService;

  @Autowired
  protected RatingService ratingService;

  @Autowired
  protected SpecialFeatureService specialFeatureService;

  @Override
  @Mapping(target = "categories", source = DgsConstants.FILMREQUEST.CategoryUuids,
      qualifiedByName = "FilmMapper#categoryUuidsToCategoryEntities")
  @Mapping(target = "actors", source = DgsConstants.FILMREQUEST.ActorUuids,
      qualifiedByName = "FilmMapper#actorUuidsToActorEntities")
  @Mapping(target = "language", source = DgsConstants.FILMREQUEST.LanguageUuid,
      qualifiedByName = "FilmMapper#languageUuidToLanguageEntity")
  @Mapping(target = "originalLanguage", source = DgsConstants.FILMREQUEST.OriginalLanguageUuid,
      qualifiedByName = "FilmMapper#languageUuidToLanguageEntity")
  @Mapping(target = "rating", source = DgsConstants.FILMREQUEST.RatingUuid,
      qualifiedByName = "FilmMapper#ratingUuidToRatingEntity")
  @Mapping(target = "specialFeatures", source = DgsConstants.FILMREQUEST.SpecialFeatureUuids,
      qualifiedByName = "FilmMapper#specialFeatureUuidsToSpecialFeatureEntities")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract FilmEntity convertToDao(FilmRequest createDto);

  @Override
  @Mapping(target = "categories", source = DgsConstants.FILMREQUEST.CategoryUuids,
      qualifiedByName = "FilmMapper#categoryUuidsToCategoryEntities")
  @Mapping(target = "actors", source = DgsConstants.FILMREQUEST.ActorUuids,
      qualifiedByName = "FilmMapper#actorUuidsToActorEntities")
  @Mapping(target = "language", source = DgsConstants.FILMREQUEST.LanguageUuid,
      qualifiedByName = "FilmMapper#languageUuidToLanguageEntity")
  @Mapping(target = "originalLanguage", source = DgsConstants.FILMREQUEST.OriginalLanguageUuid,
      qualifiedByName = "FilmMapper#languageUuidToLanguageEntity")
  @Mapping(target = "rating", source = DgsConstants.FILMREQUEST.RatingUuid,
      qualifiedByName = "FilmMapper#ratingUuidToRatingEntity")
  @Mapping(target = "specialFeatures", source = DgsConstants.FILMREQUEST.SpecialFeatureUuids,
      qualifiedByName = "FilmMapper#specialFeatureUuidsToSpecialFeatureEntities")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget FilmEntity entity, FilmRequest updateDto);


  @Named("FilmMapper#actorUuidsToActorEntities")
  protected List<ActorEntity> actorUuidsToActorEntities(List<UUID> uuids) {
    return uuids != null && !uuids.isEmpty() ? actorService.getAllByUids(uuids) : Collections.emptyList();
  }

  @Named("FilmMapper#categoryUuidsToCategoryEntities")
  protected List<CategoryEntity> categoryUuidsToCategoryEntities(List<UUID> uuids) {
    return uuids != null && !uuids.isEmpty() ? categoryService.getAllByUids(uuids) : Collections.emptyList();
  }

  @Named("FilmMapper#languageUuidToLanguageEntity")
  protected LanguageEntity languageUuidToLanguageEntity(UUID uuid) {
    return uuid != null ? languageService.getByUid(uuid) : null;
  }

  @Named("FilmMapper#ratingUuidToRatingEntity")
  protected RatingEntity ratingUuidToRatingEntity(UUID uuid) {
    return uuid != null ? ratingService.getByUid(uuid) : null;
  }

  @Named("FilmMapper#specialFeatureUuidsToSpecialFeatureEntities")
  protected List<SpecialFeatureEntity> specialFeatureUuidsToSpecialFeatureEntities(List<UUID> uuids) {
    return uuids != null && !uuids.isEmpty() ? specialFeatureService.getAllByUids(uuids) : Collections.emptyList();
  }

}
