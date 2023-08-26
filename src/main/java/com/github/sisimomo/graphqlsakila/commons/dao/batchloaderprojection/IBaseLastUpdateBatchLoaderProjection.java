package com.github.sisimomo.graphqlsakila.commons.dao.batchloaderprojection;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;

public interface IBaseLastUpdateBatchLoaderProjection {

  @Value("#{target.create_date}")
  Instant getCreateDate();

  @Value("#{target.update_date}")
  Instant getUpdateDate();

}
