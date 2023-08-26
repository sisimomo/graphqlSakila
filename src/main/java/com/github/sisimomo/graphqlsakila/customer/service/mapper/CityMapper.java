package com.github.sisimomo.graphqlsakila.customer.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.City;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CityRequest;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CityEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CountryService;


@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {CountryMapper.class})
public abstract class CityMapper implements BaseCruMapper<CityEntity, City, CityRequest, CityRequest> {

  @Autowired
  protected CountryService countryService;

  @Override
  @Mapping(target = "country", source = DgsConstants.CITYREQUEST.CountryUuid,
      qualifiedByName = "CityMapper#countryUuidToCountryEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract CityEntity convertToDao(CityRequest createDto);

  @Override
  @Mapping(target = "country", source = DgsConstants.CITYREQUEST.CountryUuid,
      qualifiedByName = "CityMapper#countryUuidToCountryEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract void update(@MappingTarget CityEntity entity, CityRequest updateDto);


  @Named("CityMapper#countryUuidToCountryEntity")
  protected CountryEntity countryUuidToCountryEntity(UUID uuid) {
    return uuid != null ? countryService.getByUid(uuid) : null;
  }

}
