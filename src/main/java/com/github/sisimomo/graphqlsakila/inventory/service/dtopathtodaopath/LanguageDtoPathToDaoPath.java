package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LanguageDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.LANGUAGE.Uuid, LanguageEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.LANGUAGE.CreateDate, LanguageEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.LANGUAGE.UpdateDate, LanguageEntity_.UPDATE_DATE, true, true),
  NAME(DgsConstants.LANGUAGE.Name, LanguageEntity_.NAME, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  LanguageDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
