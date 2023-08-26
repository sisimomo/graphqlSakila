package com.github.sisimomo.graphqlsakila.customer.dao.entity;

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

@Entity
@Table(name = "country",
    uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID}),
        @UniqueConstraint(columnNames = {CountryEntity_.COUNTRY})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  @Size(max = 50)
  private String country;

}
