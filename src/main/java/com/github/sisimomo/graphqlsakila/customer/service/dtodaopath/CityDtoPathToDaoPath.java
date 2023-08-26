package com.github.sisimomo.graphqlsakila.customer.service.dtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CityEntity_;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CityDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.CITY.Uuid, CityEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.CITY.CreateDate, CityEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.CITY.UpdateDate, CityEntity_.UPDATE_DATE, true, true),
  COUNTRY(DgsConstants.CITY.Country, CityEntity_.COUNTRY, CountryDtoPathToDaoPath.class, true, true),
  CITY(DgsConstants.CITY.City, CityEntity_.CITY, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  CityDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
