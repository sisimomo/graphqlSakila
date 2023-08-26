package com.github.sisimomo.graphqlsakila.business.dao.entity;

import java.time.Instant;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  private Instant rentalDate;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_id")
  private FilmCopyEntity filmCopy;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "staff_id")
  private StaffMemberEntity staffMember;

  private Instant returnDate;

}
