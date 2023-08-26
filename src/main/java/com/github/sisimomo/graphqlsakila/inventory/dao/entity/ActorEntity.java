package com.github.sisimomo.graphqlsakila.inventory.dao.entity;

import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.ActorBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidEntity_;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * KEEP IN SYNC
 * {@link ActorBatchLoaderProjection
 * Batch Loader Projection}.
 */
@Entity
@Table(name = "actor", uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActorEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  @Size(max = 45)
  private String firstName;

  @NotNull
  @Size(max = 45)
  private String lastName;

}
