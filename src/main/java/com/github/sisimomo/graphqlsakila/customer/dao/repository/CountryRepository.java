package com.github.sisimomo.graphqlsakila.customer.dao.repository;

import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity;

public interface CountryRepository extends CrudUuidEntityGraphJpaRepository<CountryEntity, Long>,
    WhereInAndOrderByCaseRepository<CountryEntity>, JpaSpecificationExecutor<CountryEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

  Optional<CountryEntity> findByCountry(String country);

}
