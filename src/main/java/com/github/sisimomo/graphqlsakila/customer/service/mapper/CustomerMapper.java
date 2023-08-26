package com.github.sisimomo.graphqlsakila.customer.service.mapper;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.service.StoreService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StoreMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Customer;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerCreate;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerUpdate;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;

@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {AddressMapper.class, StoreMapper.class})
public abstract class CustomerMapper
    implements BaseCruMapper<CustomerEntity, Customer, CustomerCreate, CustomerUpdate> {

  @Autowired
  protected StoreService storeService;

  @Override
  @Mapping(target = "active", constant = "true")
  @Mapping(target = "store", source = DgsConstants.CUSTOMERCREATE.StoreUuid,
      qualifiedByName = "CustomerMapper#storeUuidToStoreEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract CustomerEntity convertToDao(CustomerCreate createDto);

  @Override
  @Mapping(target = "store", source = DgsConstants.CUSTOMERCREATE.StoreUuid,
      qualifiedByName = "CustomerMapper#storeUuidToStoreEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget CustomerEntity entity, CustomerUpdate updateDto);

  @Named("CustomerMapper#storeUuidToStoreEntity")
  protected StoreEntity storeUuidToStoreEntity(UUID uuid) {
    return uuid != null ? storeService.getByUid(uuid) : null;
  }

}
