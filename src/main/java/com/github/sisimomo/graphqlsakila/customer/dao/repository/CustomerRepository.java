package com.github.sisimomo.graphqlsakila.customer.dao.repository;

import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;
import java.util.Optional;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends CrudUuidEntityGraphJpaRepository<CustomerEntity, Long>,
    WhereInAndOrderByCaseRepository<CustomerEntity>, JpaSpecificationExecutor<CustomerEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
