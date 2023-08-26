package com.github.sisimomo.graphqlsakila.business.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;

public interface RentalRepository extends CrudUuidEntityGraphJpaRepository<RentalEntity, Long>,
    WhereInAndOrderByCaseRepository<RentalEntity>, JpaSpecificationExecutor<RentalEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
