package com.github.sisimomo.graphqlsakila.inventory.dao.repository;

import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.CategoryBatchLoaderProjection;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.github.sisimomo.graphqlsakila.commons.dao.repository.CrudUuidEntityGraphJpaRepository;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;

public interface CategoryRepository
    extends CrudUuidEntityGraphJpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {

  @Override
  default Optional<EntityGraph> defaultEntityGraph() {
    return EntityGraph.NOOP.execute(Optional::of);
  }

  List<CategoryEntity> findByUuidIn(Collection<UUID> uuids);

  @Query(
      value = "SELECT DISTINCT a.*, c.uuid AS batch_loader_uuid_helper FROM category AS a JOIN film_category AS b ON b.category_id = a.id JOIN film AS c ON c.id = b.film_id WHERE c.uuid IN(?1)",
      nativeQuery = true)
  List<CategoryBatchLoaderProjection> findAllByFilmUuids(List<UUID> uuids);

}
