package com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection;

import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseLastUpdateBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidHelperBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.IBaseLongEntity;

/**
 * Batch Loader Projection of
 * {@link SpecialFeatureEntity
 * SpecialFeatureEntity}.
 */
public interface SpecialFeatureBatchLoaderProjection extends IBaseLongEntity, IBaseUuidBatchLoaderProjection,
    IBaseLastUpdateBatchLoaderProjection, IBaseUuidHelperBatchLoaderProjection {
  String getName();

}
