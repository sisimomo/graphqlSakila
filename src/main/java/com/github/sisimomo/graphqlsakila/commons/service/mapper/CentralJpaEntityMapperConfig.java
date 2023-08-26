package com.github.sisimomo.graphqlsakila.commons.service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {IsInitializedMapperCondition.class})
public interface CentralJpaEntityMapperConfig {

}
