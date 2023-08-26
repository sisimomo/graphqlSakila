package com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection;

import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseLastUpdateBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidHelperBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.IBaseLongEntity;

/**
 * Batch Loader Projection of {@link CategoryEntity
 * CategoryEntity}.
 */
public interface CategoryBatchLoaderProjection extends IBaseLongEntity, IBaseUuidBatchLoaderProjection,
    IBaseLastUpdateBatchLoaderProjection, IBaseUuidHelperBatchLoaderProjection {
  String getName();

}
