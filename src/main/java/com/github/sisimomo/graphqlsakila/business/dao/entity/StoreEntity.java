package com.github.sisimomo.graphqlsakila.business.dao.entity;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "manager_staff_id")
  private StaffMemberEntity manager;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "address_id")
  private AddressEntity address;

}
