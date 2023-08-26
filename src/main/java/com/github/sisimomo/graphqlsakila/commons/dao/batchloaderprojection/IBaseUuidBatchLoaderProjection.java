package com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

public interface IBaseUuidBatchLoaderProjection {
  @Value("#{T(com.github.sisimomo.graphqlsakila.commons.utils.BytesToUuidUtils).getUuidFromByteArray(target.uuid)}")
  UUID getUuid();

}
