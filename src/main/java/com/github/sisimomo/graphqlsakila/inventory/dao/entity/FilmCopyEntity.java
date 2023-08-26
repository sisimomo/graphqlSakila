package com.github.sisimomo.graphqlsakila.inventory.dao.entity;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidEntity_;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory", uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilmCopyEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "film_id")
  private FilmEntity film;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private StoreEntity store;

}
