package com.github.sisimomo.graphqlsakila.business.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.business.dao.entity.PaymentEntity_;
import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.customer.service.dtodaopath.CustomerDtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.PAYMENT.Uuid, PaymentEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.PAYMENT.CreateDate, PaymentEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.PAYMENT.UpdateDate, PaymentEntity_.UPDATE_DATE, true, true),
  CUSTOMER(DgsConstants.PAYMENT.Customer, PaymentEntity_.CUSTOMER, CustomerDtoPathToDaoPath.class, true, true),
  STAFF_MEMBER(DgsConstants.PAYMENT.StaffMember, PaymentEntity_.STAFF_MEMBER, StaffMemberDtoPathToDaoPath.class, true,
      true),
  RENTAL(DgsConstants.PAYMENT.Rental, PaymentEntity_.RENTAL, RentalDtoPathToDaoPath.class, true, true),
  AMOUNT(DgsConstants.PAYMENT.Amount, PaymentEntity_.AMOUNT, true, true),
  PAYMENT_DATE(DgsConstants.PAYMENT.PaymentDate, PaymentEntity_.PAYMENT_DATE, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  PaymentDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
