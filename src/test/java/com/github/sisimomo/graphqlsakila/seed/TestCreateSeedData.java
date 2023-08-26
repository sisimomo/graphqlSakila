package com.github.sisimomo.graphqlsakila.seed;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.util.Pair;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.service.PaymentServiceTest;
import com.github.sisimomo.graphqlsakila.business.service.RentalServiceTest;
import com.github.sisimomo.graphqlsakila.business.service.StaffMemberServiceTest;
import com.github.sisimomo.graphqlsakila.business.service.StoreServiceTest;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.customer.service.CustomerServiceTest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopyCreate;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.ActorServiceTest;
import com.github.sisimomo.graphqlsakila.inventory.service.CategoryServiceTest;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmCopyService;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmServiceTest;
import com.github.sisimomo.graphqlsakila.inventory.service.LanguageServiceTest;
import com.github.sisimomo.graphqlsakila.inventory.service.RatingServiceTest;
import com.github.sisimomo.graphqlsakila.inventory.service.SpecialFeatureServiceTest;
import com.github.sisimomo.graphqlsakila.test.TestInitService;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = {"decorator.datasource.datasource-proxy.query.enable-logging=false"})
public class TestCreateSeedData {

  public static final int DIVIDER = 5;

  public static final Pair<Integer, Integer> NB_STORE = Pair.of(25 / DIVIDER, 50 / DIVIDER);
  public static final Pair<Integer, Integer> NB_STAFF_PER_STORE = Pair.of(5, 15);
  public static final Pair<Integer, Integer> NB_CUSTOMER_PER_STORE = Pair.of(100, 125);
  public static final int STORE_WITHOUT_MANAGER_DICE_SIZE = 50;
  public static final Pair<Integer, Integer> NB_ACTOR = Pair.of(500 / DIVIDER, 750 / DIVIDER);
  public static final Pair<Integer, Integer> NB_LANGUAGE = Pair.of(10, 15);
  public static final Pair<Integer, Integer> NB_FILM = Pair.of(500 / DIVIDER, 750 / DIVIDER);
  public static final int FILM_WITHOUT_DESCRIPTION_DICE_SIZE = 15;
  public static final int FILM_WITHOUT_RELEASE_YEAR_DICE_SIZE = 25;
  public static final int FILM_WITHOUT_ORIGINAL_LANGUAGE_DICE_SIZE = 5;
  public static final int FILM_WITHOUT_LENGTH_DICE_SIZE = 5;
  public static final int FILM_WITHOUT_ACTORS_DICE_SIZE = 25;
  public static final Pair<Integer, Integer> FILM_ACTORS_SIZE = Pair.of(7, 25);
  public static final int FILM_WITHOUT_CATEGORIES_DICE_SIZE = 25;
  public static final Pair<Integer, Integer> FILM_CATEGORIES_SIZE = Pair.of(1, 4);
  public static final List<Integer> FILM_RENTAL_DURATION = List.of(1, 2, 3);
  public static final Pair<BigDecimal, BigDecimal> FILM_RENTAL_DAY_RATE =
      Pair.of(new BigDecimal(2), new BigDecimal(10));
  public static final Pair<BigDecimal, BigDecimal> FILM_REPLACEMENT_COST =
      Pair.of(new BigDecimal(20), new BigDecimal(500));
  public static final int FILM_WITHOUT_RATING_DICE_SIZE = 5;
  public static final int FILM_WITHOUT_SPECIAL_FEATURES_DICE_SIZE = 5;
  public static final Pair<Integer, Integer> FILM_SPECIAL_FEATURES_SIZE = Pair.of(1, 5);
  public static final Pair<Integer, Integer> NB_DISTINCT_FILM_COPY_PER_STORE = Pair.of(400, 600);
  public static final Pair<Integer, Integer> NB_SAME_FILM_COPY_PER_STORE = Pair.of(1, 4);
  public static final Pair<Integer, Integer> NB_RENTAL_PER_STORE = Pair.of(250, 500);
  public static final int RENTAL_MADE_BY_OTHER_STORE_STAFF_DICE_SIZE = 20;
  public static final int RENTAL_TO_OTHER_STORE_CUSTOMER_DICE_SIZE = 20;
  public static final Pair<Instant, Instant> RENTAL_DATE =
      Pair.of(Instant.now().minus(90, ChronoUnit.DAYS), Instant.now());
  public static final Pair<Duration, Duration> RENTAL_DURATION =
      Pair.of(Duration.of(1, ChronoUnit.DAYS), Duration.of(14, ChronoUnit.DAYS));

  public static final int PAYMENT_WITHOUT_RENTAL_DICE_SIZE = 15;

  public static final int RENTAL_WITHOUT_PAYMENT_DICE_SIZE = 100;
  public static final Pair<BigDecimal, BigDecimal> PAYMENT_WITHOUT_RENTAL_AMOUNT =
      Pair.of(new BigDecimal(10), new BigDecimal(30));
  public static final Pair<Instant, Instant> PAYMENT_WITHOUT_RENTAL_PAYMENT_DATE =
      Pair.of(Instant.now().minus(90, ChronoUnit.DAYS), Instant.now());

  public static final List<String> CATEGORY_NAMES = List.of("Action", "Documentary", "Horror", "Comedy", "Drama",
      "Romance", "Adventure", "Animation", "Fantasy", "Western", "Crime", "War", "Romantic comedy", "Thriller",
      "Science fiction", "Mystery", "Action comedy", "Action & Adventure", "Historical romance", "Contemporary fantasy",
      "Urban fantasy", "Dark fantasy", "Docudrama", "Musical");
  public static final List<String> RATING_NAMES = List.of("G", "PG", "PG-13", "R", "NC-17");
  public static final List<String> SPECIAL_FEATURE_NAMES =
      List.of("Audio commentary", "Documentary features", "Interviews", "Deleted footage", "Outtakes",
          "Photo galleries", "Storyboards", "Isolated music scores", "Trivia text commentary", "Games", "Film shorts",
          "TV spots", "Radio spots", "Theatrical trailers", "Teaser trailers");

  @Autowired
  private TestInitService testInitService;

  @Autowired
  private CustomerServiceTest customerServiceTest;

  @Autowired
  private StoreServiceTest storeServiceTest;

  @Autowired
  private StaffMemberServiceTest staffMemberServiceTest;

  @Autowired
  private CategoryServiceTest categoryServiceTest;

  @Autowired
  private ActorServiceTest actorServiceTest;

  @Autowired
  private LanguageServiceTest languageServiceTest;

  @Autowired
  private RatingServiceTest ratingServiceTest;

  @Autowired
  private SpecialFeatureServiceTest specialFeatureServiceTest;

  @Autowired
  private FilmServiceTest filmServiceTest;

  @Autowired
  private FilmCopyService filmCopyService;

  @Autowired
  private RentalServiceTest rentalServiceTest;

  @Autowired
  private PaymentServiceTest paymentServiceTest;

  @BeforeAll
  void resetAndInitMariaDB() {
    testInitService.resetMariaDB();
    testInitService.initMariaDB();
  }

  @Test
  @Order(1)
  @Transactional
  @Rollback(false)
  void testCreateSeedData() {

    log.info("Creating Stores");
    List<StoreEntity> storeEntities =
        IntStream.range(0, TestUtils.randomInt(NB_STORE)).mapToObj(i -> storeServiceTest.seed()).toList();

    log.info("Creating customers");
    List<CustomerEntity> customerEntities =
        customerServiceTest.seed(storeEntities.stream().map(StoreEntity::getUuid).toList(),
            storeEntities.size() * TestUtils.randomInt(NB_CUSTOMER_PER_STORE));

    log.info("Creating staff members");
    List<StaffMemberEntity> staffEntities =
        staffMemberServiceTest.seed(storeEntities.stream().map(StoreEntity::getUuid).toList(), getStaffPictures(),
            storeEntities.size() * TestUtils.randomInt(NB_STAFF_PER_STORE));

    log.info("Updating staff members");
    List<StoreEntity> updatedStoreEntities = storeEntities.stream()
        .map(store -> !TestUtils.rollDice(STORE_WITHOUT_MANAGER_DICE_SIZE) ? storeServiceTest
            .setManager(store.getUuid(), staffEntities.stream().map(StaffMemberEntity::getUuid).toList()) : store)
        .toList();

    List<FilmCopyEntity> filmCopyEntities = seedInventory(updatedStoreEntities);

    seedRentalAndPayment(staffEntities, customerEntities, updatedStoreEntities, filmCopyEntities);
  }

  private List<FilmCopyEntity> seedInventory(List<StoreEntity> storeEntities) {
    log.info("Creating categories");
    List<CategoryEntity> categoryEntities = CATEGORY_NAMES.stream().map(categoryServiceTest::seed).toList();

    log.info("Creating actors");
    List<ActorEntity> actorEntities =
        IntStream.range(0, TestUtils.randomInt(NB_ACTOR)).mapToObj(i -> actorServiceTest.seed()).toList();

    log.info("Creating languages");
    List<String> locales = TestUtils.getFaker().locality().allSupportedLocales();
    Collections.shuffle(locales);
    List<LanguageEntity> languageEntities = locales.stream().limit(TestUtils.randomInt(NB_LANGUAGE))
        .map(locale -> languageServiceTest.seed(locale)).toList();

    log.info("Creating ratings");
    List<RatingEntity> ratingEntities = RATING_NAMES.stream().map(ratingServiceTest::seed).toList();

    log.info("Creating special Features");
    List<SpecialFeatureEntity> specialFeatureEntities =
        SPECIAL_FEATURE_NAMES.stream().map(specialFeatureServiceTest::seed).toList();

    log.info("Creating films");
    List<FilmEntity> filmEntities = filmServiceTest.seed(IntStream.range(0, TestUtils.randomInt(NB_FILM)).mapToObj(
        i -> Quintet.with(categoryEntities, actorEntities, languageEntities, ratingEntities, specialFeatureEntities))
        .toList());

    log.info("Creating film copies");
    return filmCopyService.createAll(storeEntities.stream()
        .flatMap(storeEntity -> IntStream.range(0, TestUtils.randomInt(NB_DISTINCT_FILM_COPY_PER_STORE)).boxed()
            .map(i -> FilmCopyCreate.newBuilder().filmUuid(TestUtils.getRandomItem(filmEntities).getUuid())
                .storeUuid(storeEntity.getUuid()).build()))
        .toList());
  }

  private void seedRentalAndPayment(List<StaffMemberEntity> staffEntities, List<CustomerEntity> customerEntities,
      List<StoreEntity> storeEntities, List<FilmCopyEntity> filmCopyEntities) {

    log.info("Creating rentals");
    List<RentalEntity> rentalEntities = rentalServiceTest.seed(storeEntities.stream().flatMap(store -> {
      List<StaffMemberEntity> storeStaff =
          staffEntities.stream().filter(s -> s.getStore().getUuid().equals(store.getUuid())).toList();
      List<StaffMemberEntity> otherStoreStaff =
          staffEntities.stream().filter(s -> !s.getStore().getUuid().equals(store.getUuid())).toList();
      List<CustomerEntity> storeCustomers =
          customerEntities.stream().filter(s -> s.getStore().getUuid().equals(store.getUuid())).toList();
      List<CustomerEntity> otherStoreCustomers =
          customerEntities.stream().filter(s -> !s.getStore().getUuid().equals(store.getUuid())).toList();
      return IntStream.range(0, TestUtils.randomInt(NB_RENTAL_PER_STORE)).mapToObj(i -> {
        StaffMemberEntity staffMemberEntity =
            TestUtils.rollDice(RENTAL_MADE_BY_OTHER_STORE_STAFF_DICE_SIZE) ? TestUtils.getRandomItem(otherStoreStaff)
                : TestUtils.getRandomItem(storeStaff);
        CustomerEntity customerEntity =
            TestUtils.rollDice(RENTAL_TO_OTHER_STORE_CUSTOMER_DICE_SIZE) ? TestUtils.getRandomItem(otherStoreCustomers)
                : TestUtils.getRandomItem(storeCustomers);
        return Triplet.with(staffMemberEntity, customerEntity, TestUtils.getRandomItem(filmCopyEntities));
      });
    }).toList());

    log.info("Creating payments");
    paymentServiceTest.seed(rentalEntities.stream().filter(
        rentalEntity -> !TestUtils.rollDice(RENTAL_WITHOUT_PAYMENT_DICE_SIZE) && rentalEntity.getReturnDate() != null)
        .toList());

    paymentServiceTest.seed(IntStream.range(0, rentalEntities.size() / PAYMENT_WITHOUT_RENTAL_DICE_SIZE).boxed()
        .map(rentalEntity -> Pair.of(TestUtils.getRandomItem(staffEntities), TestUtils.getRandomItem(customerEntities)))
        .toList());
  }

  private List<MockMultipartFile> getStaffPictures() {
    try {
      return Arrays
          .stream(new PathMatchingResourcePatternResolver().getResources("classpath:/seed-data/staff-picture/*"))
          .map(a -> {
            try {
              return TestUtils.pathToMockMultipartFile(Path.of(a.getURI()));
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }).toList();
    } catch (IOException e) {
      throw new RuntimeException("Unable to retrieve staff pictures", e);
    }
  }

}
