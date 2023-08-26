package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilmCopyDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.FILMCOPY.Uuid, FilmCopyEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.FILMCOPY.CreateDate, FilmCopyEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.FILMCOPY.UpdateDate, FilmCopyEntity_.UPDATE_DATE, true, true),
  FILM(DgsConstants.FILMCOPY.Film, FilmCopyEntity_.FILM, true, true),
  STORE(DgsConstants.FILMCOPY.Store, FilmCopyEntity_.STORE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  FilmCopyDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
