package com.github.sisimomo.graphqlsakila.business.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.business.dao.entity.PaymentEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;

public interface PaymentRepository
    extends CrudUuidEntityGraphJpaRepository<PaymentEntity, Long>, JpaSpecificationExecutor<PaymentEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
