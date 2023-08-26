package com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection;

import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import org.springframework.beans.factory.annotation.Value;

import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseLastUpdateBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection.IBaseUuidHelperBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.IBaseLongEntity;

/**
 * Batch Loader Projection of {@link ActorEntity
 * ActorEntity}.
 */
public interface ActorBatchLoaderProjection extends IBaseLongEntity, IBaseUuidBatchLoaderProjection,
    IBaseLastUpdateBatchLoaderProjection, IBaseUuidHelperBatchLoaderProjection {

  @Value("#{target.first_name}")
  String getFirstName();

  @Value("#{target.last_name}")
  String getLastName();

}
