package com.github.sisimomo.graphqlsakila.customer.service.dtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity_;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CountryDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.COUNTRY.Uuid, CountryEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.COUNTRY.CreateDate, CountryEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.COUNTRY.UpdateDate, CountryEntity_.UPDATE_DATE, true, true),
  COUNTRY(DgsConstants.COUNTRY.Country, CountryEntity_.COUNTRY, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  CountryDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
