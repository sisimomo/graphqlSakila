package com.github.sisimomo.graphqlsakila.inventory.service;


import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import org.javatuples.Quintet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.seed.TestCreateSeedData;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Service
public class FilmServiceTest {

  @Autowired
  private FilmService service;

  public List<FilmEntity> seed(
      List<Quintet<@NotEmpty List<@NotNull CategoryEntity>, @NotEmpty List<@NotNull ActorEntity>, @NotEmpty List<@NotNull LanguageEntity>, @NotEmpty List<@NotNull RatingEntity>, @NotEmpty List<@NotNull SpecialFeatureEntity>>> quintets) {
    return service.createAll(quintets.stream().map(quintet -> {
      List<CategoryEntity> categoryEntities = quintet.getValue0();
      List<ActorEntity> actorEntities = quintet.getValue1();
      List<LanguageEntity> languageEntities = quintet.getValue2();
      List<RatingEntity> ratingEntities = quintet.getValue3();
      List<SpecialFeatureEntity> specialFeatureEntities = quintet.getValue4();
      int rentalDuration = TestUtils.getRandomItem(TestCreateSeedData.FILM_RENTAL_DURATION);
      BigDecimal replacementCost =
          TestUtils.randomFloat(TestCreateSeedData.FILM_REPLACEMENT_COST, TestUtils.randomInt(0, 2));
      return FilmRequest.newBuilder().title(TestUtils.randomPhrase(TestUtils.randomInt(1, 6), 255))
          .description(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_DESCRIPTION_DICE_SIZE)
              ? TestUtils.randomPhrase(TestUtils.randomInt(150, 250), 65535)
              : null)
          .actorUuids(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_ACTORS_DICE_SIZE)
              ? TestUtils.getRandomItem(actorEntities, TestUtils.randomInt(TestCreateSeedData.FILM_ACTORS_SIZE))
                  .stream().map(ActorEntity::getUuid).toList()
              : null)
          .categoryUuids(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_CATEGORIES_DICE_SIZE)
              ? TestUtils.getRandomItem(categoryEntities, TestUtils.randomInt(TestCreateSeedData.FILM_CATEGORIES_SIZE))
                  .stream().map(CategoryEntity::getUuid).toList()
              : null)
          .releaseYear(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_RELEASE_YEAR_DICE_SIZE)
              ? TestUtils.randomInt(1950, Year.now().getValue())
              : null)
          .languageUuid(TestUtils.getRandomItem(languageEntities).getUuid())
          .originalLanguageUuid(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_ORIGINAL_LANGUAGE_DICE_SIZE)
              ? TestUtils.getRandomItem(languageEntities).getUuid()
              : null)
          .rentalDuration(rentalDuration)
          .rentalRate(TestUtils.randomFloat(TestCreateSeedData.FILM_RENTAL_DAY_RATE, TestUtils.randomInt(0, 2))
              .multiply(new BigDecimal(rentalDuration)).min(replacementCost))
          .length(
              !TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_LENGTH_DICE_SIZE) ? TestUtils.randomInt(90, 180)
                  : null)
          .replacementCost(replacementCost)
          .ratingUuid(!TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_RATING_DICE_SIZE)
              ? TestUtils.getRandomItem(ratingEntities).getUuid()
              : null)
          .specialFeatureUuids(
              !TestUtils.rollDice(TestCreateSeedData.FILM_WITHOUT_SPECIAL_FEATURES_DICE_SIZE) ? TestUtils
                  .getRandomItem(specialFeatureEntities,
                      TestUtils.randomInt(TestCreateSeedData.FILM_SPECIAL_FEATURES_SIZE))
                  .stream().map(SpecialFeatureEntity::getUuid).toList() : null)
          .build();
    }).toList());
  }

}
