package com.github.sisimomo.graphqlsakila.customer.dao.entity;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseLongAndLastUpdateEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressEntity extends BaseLongAndLastUpdateEntity {

  @NotNull
  @Size(max = 50)
  private String address;

  @NotNull
  @Size(max = 50)
  private String address2;

  @NotNull
  @Size(max = 20)
  private String district;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id")
  private CityEntity city;

  @Size(max = 10)
  private String postalCode;

  @NotNull
  @Size(max = 20)
  private String phone;

}
