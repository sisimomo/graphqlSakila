package com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

public interface IBaseUuidHelperBatchLoaderProjection {
  @Value("#{T(com.github.sisimomo.graphqlsakila.commons.utils.BytesToUuidUtils).getUuidFromByteArray(target.batch_loader_uuid_helper)}")
  UUID getBatchLoaderUuidHelper();

}
