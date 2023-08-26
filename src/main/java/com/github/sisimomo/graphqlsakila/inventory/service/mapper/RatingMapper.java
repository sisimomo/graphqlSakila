package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Rating;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RatingRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import org.mapstruct.Mapper;


@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface RatingMapper extends BaseCruMapper<RatingEntity, Rating, RatingRequest, RatingRequest> {
}
