package com.github.sisimomo.graphqlsakila.customer.service.dtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddressDtoPathToDaoPath implements DtoPathToDaoPath {
  CREATE_DATE(DgsConstants.ADDRESS.CreateDate, AddressEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.ADDRESS.UpdateDate, AddressEntity_.UPDATE_DATE, true, true),
  CITY(DgsConstants.ADDRESS.City, AddressEntity_.CITY, CityDtoPathToDaoPath.class, true, true),
  ADDRESS(DgsConstants.ADDRESS.Address, AddressEntity_.ADDRESS, true, true),
  ADDRESS2(DgsConstants.ADDRESS.Address2, AddressEntity_.ADDRESS2, true, true),
  DISTRICT(DgsConstants.ADDRESS.District, AddressEntity_.DISTRICT, true, true),
  POSTAL_CODE(DgsConstants.ADDRESS.PostalCode, AddressEntity_.POSTAL_CODE, true, true),
  PHONE(DgsConstants.ADDRESS.Phone, AddressEntity_.PHONE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  AddressDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
