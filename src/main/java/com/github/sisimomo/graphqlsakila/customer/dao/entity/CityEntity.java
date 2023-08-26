package com.github.sisimomo.graphqlsakila.customer.dao.entity;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidEntity_;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "city",
    uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID}),
        @UniqueConstraint(columnNames = {CityEntity_.CITY, "country_id"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  @Size(max = 50)
  private String city;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id")
  private CountryEntity country;

}
