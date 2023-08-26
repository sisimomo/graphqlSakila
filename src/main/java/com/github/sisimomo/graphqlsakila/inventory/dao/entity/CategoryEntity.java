package com.github.sisimomo.graphqlsakila.inventory.dao.entity;

import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.CategoryBatchLoaderProjection;
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
 * {@link CategoryBatchLoaderProjection
 * Batch Loader Projection}.
 */
@Entity
@Table(name = "category", uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  @Size(max = 25)
  protected String name;

}
