package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeature;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeatureRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.SpecialFeatureBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import org.mapstruct.Mapper;


@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface SpecialFeatureMapper
    extends BaseCruMapper<SpecialFeatureEntity, SpecialFeature, SpecialFeatureRequest, SpecialFeatureRequest> {

  SpecialFeatureEntity projectionToDao(SpecialFeatureBatchLoaderProjection projection);

}
