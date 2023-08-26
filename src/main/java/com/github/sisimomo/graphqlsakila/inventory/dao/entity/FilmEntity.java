package com.github.sisimomo.graphqlsakila.inventory.dao.entity;

import java.math.BigDecimal;
import java.util.List;

import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidAndLongAndLastUpdateEntity;
import com.github.sisimomo.graphqlsakila.commons.dao.entity.BaseUuidEntity_;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "film", uniqueConstraints = {@UniqueConstraint(columnNames = {BaseUuidEntity_.UUID})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilmEntity extends BaseUuidAndLongAndLastUpdateEntity {

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "film_actor", joinColumns = @JoinColumn(name = "film_id"),
      inverseJoinColumns = @JoinColumn(name = "actor_id"))
  private List<ActorEntity> actors;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "film_category", joinColumns = @JoinColumn(name = "film_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private List<CategoryEntity> categories;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "language_id")
  private LanguageEntity language;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "original_language_id")
  private LanguageEntity originalLanguage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rating_id")
  private RatingEntity rating;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "film_special_feature", joinColumns = @JoinColumn(name = "film_id"),
      inverseJoinColumns = @JoinColumn(name = "special_feature_id"))
  private List<SpecialFeatureEntity> specialFeatures;

  @NotNull
  @Size(max = 255)
  private String title;

  @Lob
  @Size(max = 65535)
  private String description;

  private Integer releaseYear;

  @NotNull
  @Min(0)
  @Column(columnDefinition = "TINYINT UNSIGNED")
  private Integer rentalDuration;

  @NotNull
  @Min(0)
  @Column(columnDefinition = "decimal(5,2)")
  private BigDecimal rentalRate;

  @Min(0)
  @Column(columnDefinition = "SMALLINT UNSIGNED")
  private Integer length;

  @NotNull
  @Min(0)
  @Column(columnDefinition = "decimal(6,2)")
  private BigDecimal replacementCost;

}
