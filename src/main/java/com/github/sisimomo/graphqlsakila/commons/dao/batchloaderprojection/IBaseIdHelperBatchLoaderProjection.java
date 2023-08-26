package com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection;

import org.springframework.beans.factory.annotation.Value;

public interface IBaseIdHelperBatchLoaderProjection {
  @Value("#{target.batch_loader_id_helper}")
  Long getBatchLoaderIdHelper();

}
