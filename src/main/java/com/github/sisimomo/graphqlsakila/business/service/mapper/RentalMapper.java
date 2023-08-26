package com.github.sisimomo.graphqlsakila.business.service.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.service.StaffMemberService;
import com.github.sisimomo.graphqlsakila.business.service.error.RentalServiceError;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CustomerService;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.CustomerMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rental;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RentalRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmCopyService;
import com.github.sisimomo.graphqlsakila.inventory.service.mapper.FilmCopyMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper(config = CentralJpaEntityMapperConfig.class,
    uses = {CustomerMapper.class, StaffMemberMapper.class, FilmCopyMapper.class})
public abstract class RentalMapper implements BaseCruMapper<RentalEntity, Rental, RentalRequest, RentalRequest> {

  @Autowired
  protected StaffMemberService staffMemberService;

  @Autowired
  protected CustomerService customerService;

  @Autowired
  protected FilmCopyService filmCopyService;

  @Override
  @Mapping(target = "customer", source = DgsConstants.RENTALREQUEST.CustomerUuid,
      qualifiedByName = "RentalMapper#customerUuidToCustomerEntity")
  @Mapping(target = "staffMember", source = DgsConstants.RENTALREQUEST.StaffMemberUuid,
      qualifiedByName = "RentalMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "filmCopy", source = DgsConstants.RENTALREQUEST.FilmCopyUuid,
      qualifiedByName = "RentalMapper#filmCopyUuidToFilmCopyEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract RentalEntity convertToDao(RentalRequest createDto);

  @Override
  @Mapping(target = "customer", source = DgsConstants.RENTALREQUEST.CustomerUuid,
      qualifiedByName = "RentalMapper#customerUuidToCustomerEntity")
  @Mapping(target = "staffMember", source = DgsConstants.RENTALREQUEST.StaffMemberUuid,
      qualifiedByName = "RentalMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "filmCopy", source = DgsConstants.RENTALREQUEST.FilmCopyUuid,
      qualifiedByName = "RentalMapper#filmCopyUuidToFilmCopyEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget RentalEntity entity, RentalRequest updateDto);

  @Named("RentalMapper#staffMemberUuidToStaffEntity")
  protected StaffMemberEntity staffMemberUuidToStaffEntity(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    StaffMemberEntity entity = staffMemberService.getByUid(uuid);
    if (!entity.isActive()) {
      throw new UncheckedException(RentalServiceError.STAFF_MEMBER_MUST_BE_ACTIVE, log::warn, uuid);
    }
    return entity;
  }

  @Named("RentalMapper#customerUuidToCustomerEntity")
  protected CustomerEntity customerUuidToCustomerEntity(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    CustomerEntity entity = customerService.getByUid(uuid);
    if (!entity.isActive()) {
      throw new UncheckedException(RentalServiceError.CUSTOMER_MUST_BE_ACTIVE, log::warn, uuid);
    }
    return entity;
  }

  @Named("RentalMapper#filmCopyUuidToFilmCopyEntity")
  protected FilmCopyEntity filmCopyUuidToFilmCopyEntity(UUID uuid) {
    return uuid != null ? filmCopyService.getByUid(uuid) : null;
  }

}
