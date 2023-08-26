package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RatingDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.RATING.Uuid, RatingEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.RATING.CreateDate, RatingEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.RATING.UpdateDate, RatingEntity_.UPDATE_DATE, true, true),
  NAME(DgsConstants.RATING.Name, RatingEntity_.NAME, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  RatingDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
