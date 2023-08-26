package com.github.sisimomo.graphqlsakila.commons.dao.entity;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseUuidAndLongAndLastUpdateEntity extends BaseUuidAndLongEntity
    implements IBaseLongEntity, IBaseUuidEntity, IBaseLastUpdateEntity {

  /**
   * The date and time the entity was added to the system. This date and time is automatically set
   * using a trigger during an INSERT.
   */
  @NotNull
  protected Instant createDate;

  /**
   * The date and time the entity was added/updated to the system. This date and time is automatically
   * set using a trigger during an UPDATE.
   */
  @NotNull
  protected Instant updateDate;

  /**
   * When the entity is persisted, Set {@code updateDate} field to now.
   */
  @PreUpdate
  protected void onUpdate() {
    updateDate = Instant.now();
  }

  /**
   * When the entity is persisted for the first time, Set {@code createDate} field to now.
   */
  @Override
  @PrePersist
  protected void onCreate() {
    super.onCreate();
    this.createDate = Instant.now();
    this.updateDate = this.createDate;
  }

}
