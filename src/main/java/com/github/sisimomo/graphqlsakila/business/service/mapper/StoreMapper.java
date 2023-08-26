package com.github.sisimomo.graphqlsakila.business.service.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.service.StaffMemberService;
import com.github.sisimomo.graphqlsakila.business.service.error.StoreServiceError;
import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.AddressMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Store;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StoreRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {StaffMemberMapper.class, AddressMapper.class})
public abstract class StoreMapper implements BaseCruMapper<StoreEntity, Store, StoreRequest, StoreRequest> {

  @Autowired
  protected StaffMemberService staffMemberService;

  @Override
  @Mapping(target = "manager", source = DgsConstants.STOREREQUEST.ManagerUuid,
      qualifiedByName = "StoreMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract StoreEntity convertToDao(StoreRequest createDto);

  @Override
  @Mapping(target = "manager", source = DgsConstants.STOREREQUEST.ManagerUuid,
      qualifiedByName = "StoreMapper#staffMemberUuidToStaffEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget StoreEntity entity, StoreRequest updateDto);

  @Named("StoreMapper#staffMemberUuidToStaffEntity")
  protected StaffMemberEntity staffMemberUuidToStaffEntity(UUID uuid) {
    if (uuid == null) {
      return null;
    }
    StaffMemberEntity entity = staffMemberService.getByUid(uuid);
    if (!entity.isActive()) {
      throw new UncheckedException(StoreServiceError.STAFF_MEMBER_MUST_BE_ACTIVE, log::warn, uuid);
    }
    return entity;
  }

}
