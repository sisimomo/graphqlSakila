package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.CATEGORY.Uuid, CategoryEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.CATEGORY.CreateDate, CategoryEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.CATEGORY.UpdateDate, CategoryEntity_.UPDATE_DATE, true, true),
  NAME(DgsConstants.CATEGORY.Name, CategoryEntity_.NAME, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  CategoryDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
