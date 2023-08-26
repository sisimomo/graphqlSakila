package com.github.sisimomo.graphqlsakila.commons.dao.entity;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseUuidAndLastUpdateEntity extends BaseUuidEntity implements IBaseUuidEntity, IBaseLastUpdateEntity {

  /**
   * The date and time the entity was added to the system. This date and time is automatically set
   * using a trigger during an INSERT.
   */
  protected Instant createDate;

  /**
   * The date and time the entity was added/updated to the system. This date and time is automatically
   * set using a trigger during an UPDATE.
   */
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
    createDate = Instant.now();
    updateDate = createDate;
  }

}
