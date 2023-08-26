package com.github.sisimomo.graphqlsakila.business.service.mapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.multipart.MultipartFile;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.service.StoreService;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.customer.service.mapper.AddressMapper;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMember;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberUpdate;

@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {AddressMapper.class})
public abstract class StaffMemberMapper
    implements BaseCruMapper<StaffMemberEntity, StaffMember, StaffMemberCreate, StaffMemberUpdate> {

  @Autowired
  @Lazy
  protected StoreService storeService;

  @Override
  @Mapping(target = "picture", source = DgsConstants.STAFFMEMBER.Picture,
      qualifiedByName = "StaffMemberMapper#pictureBytesToBase64")
  public abstract StaffMember convertToDto(StaffMemberEntity entity);

  @Override
  @Mapping(target = "picture", source = DgsConstants.STAFFMEMBERCREATE.Picture,
      qualifiedByName = "StaffMemberMapper#pictureMultipartFileToBytes")
  @Mapping(target = "store", source = DgsConstants.STAFFMEMBERCREATE.StoreUuid,
      qualifiedByName = "StaffMemberMapper#storeUuidToStoreEntity")
  @Mapping(target = "active", constant = "true")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract StaffMemberEntity convertToDao(StaffMemberCreate createDto);

  @Override
  @Mapping(target = "username", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "picture", source = DgsConstants.STAFFMEMBERUPDATE.Picture,
      qualifiedByName = "StaffMemberMapper#pictureMultipartFileToBytes")
  @Mapping(target = "store", source = DgsConstants.STAFFMEMBERUPDATE.StoreUuid,
      qualifiedByName = "StaffMemberMapper#storeUuidToStoreEntity")
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget StaffMemberEntity entity, StaffMemberUpdate updateDto);

  @Named("StaffMemberMapper#storeUuidToStoreEntity")
  protected StoreEntity storeUuidToStoreEntity(UUID uuid) {
    return uuid != null ? storeService.getByUid(uuid) : null;
  }

  @Named("StaffMemberMapper#pictureBytesToBase64")
  protected String pictureBytesToBase64(byte[] bytes) {
    return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : null;
  }

  @Named("StaffMemberMapper#pictureMultipartFileToBytes")
  protected byte[] pictureMultipartFileToBytes(MultipartFile multipartFile) throws IOException {
    return multipartFile != null ? Base64.getEncoder().encodeToString(multipartFile.getBytes()).getBytes() : null;
  }

}
