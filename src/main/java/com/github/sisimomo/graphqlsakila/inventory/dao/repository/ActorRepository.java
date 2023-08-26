package com.github.sisimomo.graphqlsakila.inventory.dao.repository;

import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.ActorBatchLoaderProjection;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.WhereInAndOrderByCaseRepository;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;

public interface ActorRepository extends CrudUuidEntityGraphJpaRepository<ActorEntity, Long>,
    WhereInAndOrderByCaseRepository<ActorEntity>, JpaSpecificationExecutor<ActorEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

  List<ActorEntity> findByUuidIn(Collection<UUID> uuids);

  @Query(
      value = "SELECT DISTINCT a.*, c.uuid AS batch_loader_uuid_helper FROM actor AS a JOIN film_actor AS b ON b.actor_id = a.id JOIN film AS c ON c.id = b.film_id WHERE c.uuid IN(?1)",
      nativeQuery = true)
  List<ActorBatchLoaderProjection> findAllByFilmUuids(List<UUID> uuids);

}
