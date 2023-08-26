package com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity_;
import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.ActorDtoPathToDaoPath;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoreDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.STORE.Uuid, StoreEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.STORE.CreateDate, StoreEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.STORE.UpdateDate, StoreEntity_.UPDATE_DATE, true, true),
  ADDRESS(DgsConstants.STORE.Address, StoreEntity_.ADDRESS, ActorDtoPathToDaoPath.class, true, true),
  MANAGER(DgsConstants.STORE.Manager, StoreEntity_.MANAGER, StaffMemberDtoPathToDaoPath.class, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  StoreDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
