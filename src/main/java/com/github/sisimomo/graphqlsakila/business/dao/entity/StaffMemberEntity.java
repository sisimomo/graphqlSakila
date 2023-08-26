package com.github.sisimomo.graphqlsakila.business.dao.entity;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.converter.StringToEncryptedBytesConverter;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffMemberEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id")
  private StoreEntity store;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "address_id")
  private AddressEntity address;

  @NotNull
  @Size(max = 16)
  private String username;

  @NotNull
  @Size(max = 45)
  private String firstName;

  @NotNull
  @Size(max = 45)
  private String lastName;

  @Lob
  @Column(columnDefinition = "mediumblob")
  private byte[] picture;

  @Size(max = 50)
  private String email;

  private boolean active;

  @Column(columnDefinition = "tinyblob")
  @Convert(converter = StringToEncryptedBytesConverter.class)
  private String password;

}
