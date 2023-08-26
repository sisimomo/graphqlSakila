package com.github.sisimomo.graphqlsakila.customer.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Country;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CountryRequest;
import org.mapstruct.Mapper;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity;

@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface CountryMapper extends BaseCruMapper<CountryEntity, Country, CountryRequest, CountryRequest> {
}
