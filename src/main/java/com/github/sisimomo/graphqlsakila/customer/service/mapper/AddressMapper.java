package com.github.sisimomo.graphqlsakila.customer.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Address;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.AddressRequest;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CityEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CityService;
import com.github.sisimomo.graphqlsakila.customer.service.dto.AddressWithId;

@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {CityMapper.class})
public abstract class AddressMapper
    implements BaseCruMapper<AddressEntity, AddressWithId, AddressRequest, AddressRequest> {

  @Autowired
  protected CityService cityService;

  @Override
  @Mapping(source = "entity", target = "innerAddress", qualifiedByName = "AddressMapper#delegate")
  public abstract AddressWithId convertToDto(AddressEntity entity);

  @Override
  @Mapping(target = "city", source = DgsConstants.ADDRESSREQUEST.CityUuid,
      qualifiedByName = "AddressMapper#cityUuidToCityEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract AddressEntity convertToDao(AddressRequest createDto);

  @Override
  @Mapping(target = "city", source = DgsConstants.ADDRESSREQUEST.CityUuid,
      qualifiedByName = "AddressMapper#cityUuidToCityEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget AddressEntity entity, AddressRequest updateDto);

  @Named("AddressMapper#delegate")
  protected abstract Address convertToDtoInner(AddressEntity entity);

  @Named("AddressMapper#cityUuidToCityEntity")
  protected CityEntity cityUuidToCityEntity(UUID uuid) {
    return uuid != null ? cityService.getByUid(uuid) : null;
  }

}
