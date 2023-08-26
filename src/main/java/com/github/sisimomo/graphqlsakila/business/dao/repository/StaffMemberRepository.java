package com.github.sisimomo.graphqlsakila.business.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;

public interface StaffMemberRepository extends CrudUuidEntityGraphJpaRepository<StaffMemberEntity, Long>,
    WhereInAndOrderByCaseRepository<StaffMemberEntity>, JpaSpecificationExecutor<StaffMemberEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
