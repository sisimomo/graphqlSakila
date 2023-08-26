package com.github.sisimomo.graphqlsakila.customer.dao.repository;

import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;
import java.util.Optional;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;

public interface AddressRepository
    extends EntityGraphJpaRepository<AddressEntity, Long>, WhereInAndOrderByCaseRepository<AddressEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
