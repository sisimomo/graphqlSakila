package com.github.sisimomo.graphqlsakila.inventory.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;

public interface FilmCopyRepository extends CrudUuidEntityGraphJpaRepository<FilmCopyEntity, Long>,
    WhereInAndOrderByCaseRepository<FilmCopyEntity>, JpaSpecificationExecutor<FilmCopyEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

}
