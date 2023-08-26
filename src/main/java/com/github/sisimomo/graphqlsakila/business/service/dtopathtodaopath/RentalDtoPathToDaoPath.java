package com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity_;
import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CustomerDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath.FilmCopyDtoPathToDaoPath;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RentalDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.RENTAL.Uuid, RentalEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.RENTAL.CreateDate, RentalEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.RENTAL.UpdateDate, RentalEntity_.UPDATE_DATE, true, true),
  FILM_COPY(DgsConstants.RENTAL.FilmCopy, RentalEntity_.FILM_COPY, FilmCopyDtoPathToDaoPath.class, true, true),
  CUSTOMER(DgsConstants.RENTAL.Customer, RentalEntity_.CUSTOMER, CustomerDtoPathToDaoPath.class, true, true),
  STAFF_MEMBER(DgsConstants.RENTAL.StaffMember, RentalEntity_.STAFF_MEMBER, StaffMemberDtoPathToDaoPath.class, true,
      true),
  RENTAL_DATE(DgsConstants.RENTAL.RentalDate, RentalEntity_.RENTAL_DATE, true, true),
  RETURN_DATE(DgsConstants.RENTAL.ReturnDate, RentalEntity_.RETURN_DATE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  RentalDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
