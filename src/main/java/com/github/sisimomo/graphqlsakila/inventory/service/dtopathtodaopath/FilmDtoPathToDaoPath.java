package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilmDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.FILM.Uuid, FilmEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.FILM.CreateDate, FilmEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.FILM.UpdateDate, FilmEntity_.UPDATE_DATE, true, true),
  TITLE(DgsConstants.FILM.Title, FilmEntity_.TITLE, true, true),
  DESCRIPTION(DgsConstants.FILM.Description, FilmEntity_.DESCRIPTION, true, true),
  RELEASE_YEAR(DgsConstants.FILM.ReleaseYear, FilmEntity_.RELEASE_YEAR, true, true),
  CATEGORIES(DgsConstants.FILM.Categories, FilmEntity_.CATEGORIES, CategoryDtoPathToDaoPath.class, true, true),
  ACTORS(DgsConstants.FILM.Actors, FilmEntity_.ACTORS, ActorDtoPathToDaoPath.class, true, true),
  LANGUAGE(DgsConstants.FILM.Language, FilmEntity_.LANGUAGE, LanguageDtoPathToDaoPath.class, true, true),
  ORIGINAL_LANGUAGE(DgsConstants.FILM.OriginalLanguage, FilmEntity_.ORIGINAL_LANGUAGE, LanguageDtoPathToDaoPath.class,
      true, true),
  RENTAL_DURATION(DgsConstants.FILM.RentalDuration, FilmEntity_.RENTAL_DURATION, true, true),
  RENTAL_RATE(DgsConstants.FILM.RentalRate, FilmEntity_.RENTAL_RATE, true, true),
  LENGTH(DgsConstants.FILM.Length, FilmEntity_.LENGTH, true, true),
  REPLACEMENT_COST(DgsConstants.FILM.ReplacementCost, FilmEntity_.REPLACEMENT_COST, true, true),
  RATING(DgsConstants.FILM.Rating, FilmEntity_.RATING, RatingDtoPathToDaoPath.class, true, true),
  SPECIAL_FEATURES(DgsConstants.FILM.SpecialFeatures, FilmEntity_.SPECIAL_FEATURES,
      SpecialFeatureDtoPathToDaoPath.class, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  FilmDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
