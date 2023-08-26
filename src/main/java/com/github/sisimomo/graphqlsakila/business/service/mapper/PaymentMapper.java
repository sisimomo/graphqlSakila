package com.github.sisimomo.graphqlsakila.business.service.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.business.dao.entity.PaymentEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.service.RentalService;
import com.github.sisimomo.graphqlsakila.business.service.StaffMemberService;
import com.github.sisimomo.graphqlsakila.business.service.error.PaymentServiceError;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CustomerService;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CustomerMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Payment;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper(config = CentralJpaEntityMapperConfig.class,
    uses = {CustomerMapper.class, StaffMemberMapper.class, RentalMapper.class})
public abstract class PaymentMapper implements BaseCruMapper<PaymentEntity, Payment, PaymentCreate, PaymentUpdate> {

  @Autowired
  protected StaffMemberService staffMemberService;

  @Autowired
  protected CustomerService customerService;

  @Autowired
  protected RentalService rentalService;

  @Override
  @Mapping(target = "customer", source = DgsConstants.PAYMENTCREATE.CustomerUuid,
      qualifiedByName = "PaymentMapper#customerUuidToCustomerEntity")
  @Mapping(target = "staffMember", source = DgsConstants.PAYMENTCREATE.StaffMemberUuid,
      qualifiedByName = "PaymentMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "rental", source = DgsConstants.PAYMENTCREATE.RentalUuid,
      qualifiedByName = "PaymentMapper#rentalUuidToRentalEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract PaymentEntity convertToDao(PaymentCreate createDto);

  @Override
  @Mapping(target = "paymentDate", ignore = true)
  @Mapping(target = "customer", source = DgsConstants.PAYMENTUPDATE.CustomerUuid,
      qualifiedByName = "PaymentMapper#customerUuidToCustomerEntity")
  @Mapping(target = "staffMember", source = DgsConstants.PAYMENTUPDATE.StaffMemberUuid,
      qualifiedByName = "PaymentMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "rental", source = DgsConstants.PAYMENTUPDATE.RentalUuid,
      qualifiedByName = "PaymentMapper#rentalUuidToRentalEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget PaymentEntity entity, PaymentUpdate updateDto);

  @Named("PaymentMapper#staffMemberUuidToStaffEntity")
  protected StaffMemberEntity staffMemberUuidToStaffEntity(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    StaffMemberEntity entity = staffMemberService.getByUid(uuid);
    if (!entity.isActive()) {
      throw new UncheckedException(PaymentServiceError.STAFF_MEMBER_MUST_BE_ACTIVE, log::warn, uuid);
    }
    return entity;
  }

  @Named("PaymentMapper#customerUuidToCustomerEntity")
  protected CustomerEntity customerUuidToCustomerEntity(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    CustomerEntity entity = customerService.getByUid(uuid);
    if (!entity.isActive()) {
      throw new UncheckedException(PaymentServiceError.CUSTOMER_MUST_BE_ACTIVE, log::warn, uuid);
    }
    return entity;
  }

  @Named("PaymentMapper#rentalUuidToRentalEntity")
  protected RentalEntity rentalUuidToRentalEntity(UUID uuid) {
    return uuid != null ? rentalService.getByUid(uuid) : null;
  }

}
