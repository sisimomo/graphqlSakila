package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpecialFeatureDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.SPECIALFEATURE.Uuid, SpecialFeatureEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.SPECIALFEATURE.CreateDate, SpecialFeatureEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.SPECIALFEATURE.UpdateDate, SpecialFeatureEntity_.UPDATE_DATE, true, true),
  NAME(DgsConstants.SPECIALFEATURE.Name, SpecialFeatureEntity_.NAME, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  SpecialFeatureDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
