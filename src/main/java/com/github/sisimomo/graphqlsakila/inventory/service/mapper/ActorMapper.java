package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Actor;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.ActorRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.ActorBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import org.mapstruct.Mapper;


@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface ActorMapper extends BaseCruMapper<ActorEntity, Actor, ActorRequest, ActorRequest> {

  ActorEntity projectionToDao(ActorBatchLoaderProjection projection);

}
