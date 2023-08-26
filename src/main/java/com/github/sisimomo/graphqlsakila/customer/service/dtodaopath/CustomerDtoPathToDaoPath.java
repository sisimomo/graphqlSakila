package com.github.sisimomo.graphqlsakila.customer.service.dtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity_;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath.StoreDtoPathToDaoPath;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.CUSTOMER.Uuid, CustomerEntity_.UUID, true, true),
  UPDATE_DATE(DgsConstants.CUSTOMER.UpdateDate, CustomerEntity_.UPDATE_DATE, true, true),
  CREATE_DATE(DgsConstants.CUSTOMER.CreateDate, CustomerEntity_.CREATE_DATE, true, true),
  STORE(DgsConstants.CUSTOMER.Store, CustomerEntity_.STORE, StoreDtoPathToDaoPath.class, true, true),
  FIRST_NAME(DgsConstants.CUSTOMER.FirstName, CustomerEntity_.FIRST_NAME, true, true),
  LAST_NAME(DgsConstants.CUSTOMER.LastName, CustomerEntity_.LAST_NAME, true, true),
  EMAIL(DgsConstants.CUSTOMER.Email, CustomerEntity_.EMAIL, true, true),
  ADDRESS(DgsConstants.CUSTOMER.Address, CustomerEntity_.ADDRESS, AddressDtoPathToDaoPath.class, true, true),
  ACTIVE(DgsConstants.CUSTOMER.Active, CustomerEntity_.ACTIVE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  CustomerDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
