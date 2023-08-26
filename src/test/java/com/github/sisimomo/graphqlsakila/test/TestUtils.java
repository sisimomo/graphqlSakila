package com.github.sisimomo.graphqlsakila.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.util.Pair;
import org.springframework.mock.web.MockMultipartFile;

import net.datafaker.Faker;

public class TestUtils {

  // We don't care about reusing the same random generator, this is for testing
  private static final Random RANDOM = new Random();

  private static final Faker faker = new Faker();

  public static Faker getFaker() {
    return faker;
  }

  /**
   * Get a random item from a {@link Map}.
   *
   * @param items The {@link Map} of items to choose from.
   * @return A random key-value pair from the map.
   */
  public static <T, U> Map.Entry<T, U> getRandomItem(Map<T, U> items) {
    T randomKey = getRandomItem(items.keySet());
    return Map.entry(randomKey, items.get(randomKey));
  }

  /**
   * Get a {@link Map} with the specified number of items randomly selected from the provided
   * {@link Map}.
   *
   * @param items The {@link Map} of items to choose from.
   * @param numberOfItems The number of items to return.
   * @return A {@link Map} of random values.
   */
  public static <T, U> Map<T, U> getRandomItem(Map<T, U> items, int numberOfItems) {
    return getRandomItem(items.keySet(), numberOfItems).stream().map(key -> Map.entry(key, items.get(key)))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Return a random item from a {@link Set}.
   *
   * @param items The {@link Set} of items to choose from.
   * @return A random item from the {@link Set}.
   */
  public static <T> T getRandomItem(Set<T> items) {
    return getSetItemByIndex(items, randomInt(items.size()));
  }

  private static <T> T getSetItemByIndex(Set<T> items, int itemIndex) {
    int i = 0;
    for (T obj : items) {
      if (i == itemIndex) {
        return obj;
      }
      i++;
    }
    throw new RuntimeException();
  }

  /**
   * Get a {@link Set} with the specified number of items randomly selected from the provided
   * {@link Set}.
   *
   * @param items The {@link Set} of items to choose from.
   * @param numberOfItems The number of items to return.
   * @return A {@link Set} of random values.
   */
  public static <T> Set<T> getRandomItem(Set<T> items, int numberOfItems) {
    if (items.size() <= numberOfItems) {
      return items;
    }
    return randomUniqueInt(items.size(), numberOfItems).stream().map(i -> getSetItemByIndex(items, i))
        .collect(Collectors.toSet());
  }

  /**
   * Return a random item from a {@link List}.
   *
   * @param items The {@link List} of items to choose from.
   * @return A random item from the {@link List}.
   */
  public static <T> T getRandomItem(List<T> items) {
    return items.get(randomInt(items.size()));
  }

  /**
   * Get a {@link List} with the specified number of items randomly selected from the provided
   * {@link List}.
   *
   * @param items The {@link List} of items to choose from.
   * @param numberOfItems The number of items to return.
   * @return A {@link List} of random values.
   */
  public static <T> List<T> getRandomItem(List<T> items, int numberOfItems) {
    if (items.size() <= numberOfItems) {
      return items;
    }
    return randomUniqueInt(items.size(), numberOfItems).stream().map(items::get).collect(Collectors.toList());
  }

  /**
   * Return a random item from an array.
   *
   * @param items The array of items to choose from.
   * @return A random item from the array.
   */
  public static <T> T getRandomItem(T[] items) {
    return items[randomInt(items.length)];
  }

  /**
   * Get an array with the specified number of items randomly selected from the provided array.
   *
   * @param items The array of items to choose from.
   * @param numberOfItems The number of items to return.
   * @return An array of random values.
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] getRandomItem(T[] items, int numberOfItems) {
    if (items.length <= numberOfItems) {
      return items;
    }
    return (T[]) randomUniqueInt(items.length, numberOfItems).stream().map(i -> items[i]).toArray();
  }

  private static Set<Integer> randomUniqueInt(int maxExclusive, int count) {
    Set<Integer> ints = new HashSet<>();
    while (ints.size() < count) {
      ints.add(randomInt(maxExclusive));
    }
    return ints;
  }

  public static MockMultipartFile pathToMockMultipartFile(Path path) {
    return TestUtils.pathToMockMultipartFile(path, path.getFileName().toString().split("\\.")[0]);
  }

  public static MockMultipartFile pathToMockMultipartFile(Path path, String name) {
    try {
      return new MockMultipartFile(name, Files.readAllBytes(path));
    } catch (IOException e) {
      throw new RuntimeException("Fail to read the file");
    }
  }

  public static String trimStringLength(String str, int maxLength) {
    return str.substring(0, Math.min(str.length(), maxLength));
  }

  public static String randomPhrase(int nbWords, int maxLength) {
    String phrase = IntStream.of(0, nbWords).mapToObj(i -> getRandomWord()).collect(Collectors.joining(" "));
    return trimStringLength(phrase, maxLength);
  }

  public static String getRandomEmail() {
    return faker.internet().emailAddress();
  }

  public static String getRandomUrl() {
    return faker.internet().url();
  }

  public static String getRandomPhoneNumbers() {
    return faker.phoneNumber().phoneNumber();
  }

  public static String getRandomWord() {
    return faker.lorem().word();
  }

  public static boolean rollDice(int nbNumbersOnDice) {
    return RANDOM.nextInt(nbNumbersOnDice + 1) == 0;
  }

  public static boolean flipCoin() {
    return RANDOM.nextInt(2) > 0;
  }

  public static int randomInt(int maxExclusive) {
    return RANDOM.nextInt(maxExclusive);
  }

  public static int randomInt(Pair<Integer, Integer> minMax) {
    return randomInt(minMax.getFirst(), minMax.getSecond());
  }

  public static int randomInt(int minInclusive, int maxInclusive) {
    // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
    return RANDOM.nextInt((maxInclusive - minInclusive) + 1) + minInclusive;
  }

  public static long randomLong(int maxExclusive) {
    return RANDOM.nextLong(maxExclusive);
  }

  public static long randomLong(long minInclusive, long maxInclusive) {
    // nextInt is normally exclusive of the top value, so add 1 to make it inclusive
    return RANDOM.nextLong((maxInclusive - minInclusive) + 1) + minInclusive;
  }

  public static LocalTime randomLocalTime(LocalTime min, LocalTime max) {
    BigDecimal tempMin = new BigDecimal(min.getHour())
        .add(new BigDecimal(min.getMinute()).divide(new BigDecimal(60), 3, RoundingMode.HALF_UP));
    BigDecimal tempMax = new BigDecimal(max.getHour())
        .add(new BigDecimal(max.getMinute()).divide(new BigDecimal(60), 3, RoundingMode.HALF_UP));
    BigDecimal random = randomFloat(tempMin, tempMax);
    LocalTime time = LocalTime.of(random.intValue(),
        random.subtract(new BigDecimal(random.intValue())).multiply(new BigDecimal(60)).intValue());
    return getNearestHourQuarter(time);
  }

  public static Duration randomDuration(Pair<Duration, Duration> minMax) {
    return randomDuration(minMax.getFirst(), minMax.getSecond());
  }

  public static Duration randomDuration(Duration startInclusive, Duration endInclusive) {
    long startSeconds = startInclusive.getSeconds();
    long endSeconds = endInclusive.getSeconds();
    return Duration.of(randomLong(startSeconds, endSeconds), ChronoUnit.SECONDS);
  }

  public static Instant randomInstant(Pair<Instant, Instant> minMax) {
    return randomInstant(minMax.getFirst(), minMax.getSecond());
  }

  public static Instant randomInstant(Instant startInclusive, Instant endInclusive) {
    long startSeconds = startInclusive.getEpochSecond();
    long endSeconds = endInclusive.getEpochSecond();
    return Instant.ofEpochSecond(randomLong(startSeconds, endSeconds));
  }

  public static LocalDate randomLocalDate(LocalDate startInclusive, LocalDate endInclusive) {
    Instant instant = randomInstant(startInclusive.atStartOfDay(ZoneOffset.UTC).toInstant(),
        endInclusive.atStartOfDay(ZoneOffset.UTC).toInstant());
    return LocalDate.ofInstant(instant, ZoneOffset.UTC);
  }

  public static LocalTime getNearestHourQuarter(LocalTime time) {
    int minutes = time.getMinute();
    int mod = minutes % 15;
    LocalTime newTime;
    if (mod < 8) {
      newTime = time.minusMinutes(mod);
    } else {
      newTime = time.plusMinutes(15 - mod);
    }
    return newTime.truncatedTo(ChronoUnit.MINUTES);
  }

  public static BigDecimal randomMonetaryAmount() {
    return TestUtils.randomFloat(TestUtils.randomInt(2), 2);
  }

  public static BigDecimal randomFloat(BigDecimal min, BigDecimal max) {
    return min.add(max.subtract(min).multiply(randomFloat(0, 2)));
  }

  public static BigDecimal randomFloat(Pair<BigDecimal, BigDecimal> minMax, int nbDecimal) {
    return randomFloat(minMax.getFirst(), minMax.getSecond(), nbDecimal);
  }

  public static BigDecimal randomFloat(BigDecimal min, BigDecimal max, int nbDecimal) {
    return min
        .add(max.multiply(new BigDecimal("10").pow(nbDecimal))
            .subtract(min.multiply(new BigDecimal("10").pow(nbDecimal))).multiply(randomFloat(0, 2)))
        .divide(new BigDecimal("10").pow(nbDecimal), nbDecimal, RoundingMode.HALF_UP);
  }

  public static BigDecimal randomFloat(int nbInteger, int nbDecimal) {
    return new BigDecimal(Double.toString(RANDOM.nextDouble() * Math.pow(10, nbInteger))).setScale(nbDecimal,
        RoundingMode.HALF_UP);
  }

}
