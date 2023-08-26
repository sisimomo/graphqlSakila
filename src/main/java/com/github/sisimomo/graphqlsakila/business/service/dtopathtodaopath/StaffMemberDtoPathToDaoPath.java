package com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity_;
import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.AddressDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StaffMemberDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.STAFFMEMBER.Uuid, StaffMemberEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.STAFFMEMBER.CreateDate, StaffMemberEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.STAFFMEMBER.UpdateDate, StaffMemberEntity_.UPDATE_DATE, true, true),
  STORE(DgsConstants.STAFFMEMBER.Store, StaffMemberEntity_.STORE, StoreDtoPathToDaoPath.class, true, true),
  ADDRESS(DgsConstants.STAFFMEMBER.Address, StaffMemberEntity_.ADDRESS, AddressDtoPathToDaoPath.class, true, true),
  USERNAME(DgsConstants.STAFFMEMBER.Username, StaffMemberEntity_.USERNAME, true, true),
  FIRST_NAME(DgsConstants.STAFFMEMBER.FirstName, StaffMemberEntity_.FIRST_NAME, true, true),
  LAST_NAME(DgsConstants.STAFFMEMBER.LastName, StaffMemberEntity_.LAST_NAME, true, true),
  PICTURE(DgsConstants.STAFFMEMBER.Picture, StaffMemberEntity_.PICTURE, false, false),
  EMAIL(DgsConstants.STAFFMEMBER.Email, StaffMemberEntity_.EMAIL, true, true),
  ACTIVE(DgsConstants.STAFFMEMBER.Active, StaffMemberEntity_.ACTIVE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  StaffMemberDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
