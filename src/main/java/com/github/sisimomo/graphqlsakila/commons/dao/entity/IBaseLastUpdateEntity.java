package com.github.sisimomo.graphqlsakila.commons.dao.entity;

import java.time.Instant;

public interface IBaseLastUpdateEntity {

  Instant getCreateDate();

  Instant getUpdateDate();

}
