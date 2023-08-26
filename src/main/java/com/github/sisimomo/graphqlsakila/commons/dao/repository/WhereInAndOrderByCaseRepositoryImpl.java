package com.github.sisimomo.graphqlsakila.commons.dao.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;

public class WhereInAndOrderByCaseRepositoryImpl<T> implements WhereInAndOrderByCaseRepository<T> {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Finds all entities of a given class by a specified column, with optional filtering and ordering.
   *
   * @param baseSqlQuery The base SQL query that will be used as the foundation for the final query.
   * @param inAndOrderColumn The name of the column in the database table that the "inList" parameter
   *        will be used to filter on and the name of the column by which the result should be
   *        ordered.
   * @param inAndOrderList A list of values to be used in the WHERE IN clause of the SQL query and a
   *        list of values to be used for ordering the results of the query.
   * @param clazz The class type of the entity that the method will return a list of.
   * @return A list of objects of type T.
   */
  @Override
  public List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, String inAndOrderColumn,
      @NotNull List<?> inAndOrderList, @NotNull Class<T> clazz) {
    return findByColumnInAndOrderByCase(baseSqlQuery, inAndOrderColumn, inAndOrderList, inAndOrderColumn,
        inAndOrderList, clazz);
  }

  /**
   * Finds all entities of a given class by a specified column, with optional filtering and ordering.
   *
   * @param baseSqlQuery The base SQL query that will be used as the foundation for the final query.
   * @param inAndOrderList A list of values to be used in the WHERE IN clause of the SQL query and a
   *        list of values to be used for ordering the results of the query.
   * @param clazz The class type of the entity that the method will return a list of.
   * @return A list of objects of type T.
   */
  @Override
  public List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, @NotNull List<?> inAndOrderList,
      @NotNull Class<T> clazz) {
    return findByColumnInAndOrderByCase(baseSqlQuery, "b.uuid", inAndOrderList, "b.uuid", inAndOrderList, clazz);
  }

  /**
   * Finds all entities of a given class by a specified column with ordering.
   *
   * @param baseSqlQuery The base SQL query that will be used as the foundation for the final query.
   * @param orderColumn The name of the column by which the result should be ordered.
   * @param orderList A list of values to be used for ordering the results of the query.
   * @param clazz The class type of the entity that the method will return a list of.
   * @return A list of objects of type T is being returned.
   */
  @Override
  public List<T> findOrderByCase(@NotNull String baseSqlQuery, String orderColumn, @NotNull List<?> orderList,
      @NotNull Class<T> clazz) {
    return findByColumnInAndOrderByCase(baseSqlQuery, null, null, orderColumn, orderList, clazz);
  }

  /**
   * Finds all entities of a given class by a specified column, with optional filtering and ordering.
   *
   * @param baseSqlQuery The base SQL query that will be used as the foundation for the final query.
   * @param inColumn The name of the column in the database table that the "inList" parameter will be
   *        used to filter on.
   * @param inList A list of values to be used in the WHERE IN clause of the SQL query.
   * @param orderColumn The name of the column by which the result should be ordered.
   * @param orderList A list of values to be used for ordering the results of the query.
   * @param clazz The class type of the entity that the method will return a list of.
   * @return A list of objects of type T.
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, String inColumn, List<?> inList,
      String orderColumn, List<?> orderList, @NotNull Class<T> clazz) {
    String sqlQuery = baseSqlQuery;
    if (inList != null && !inList.isEmpty()) {
      if (inColumn == null) {
        throw new IllegalArgumentException("inColumn field must be provided if inList parameter is provided");
      }
      sqlQuery += " " + whereIn(inList, inColumn);
    }
    if (orderList != null && !orderList.isEmpty()) {
      if (orderColumn == null) {
        throw new IllegalArgumentException("orderColumn field must be provided if orderList parameter is provided");
      }
      sqlQuery += " " + orderByCase(orderList, orderColumn);
    }
    return entityManager.createNativeQuery(sqlQuery, clazz).getResultList();
  }

  private String orderByCase(List<?> list, String orderColumn) {
    return IntStream.range(0, list.size()).mapToObj(i -> String.format("WHEN %s THEN %d", toSql(list.get(i)), i + 1))
        .collect(Collectors.joining(" ", String.format("ORDER BY CASE %s ", orderColumn),
            String.format(" ELSE %d END", list.size() + 1)));
  }

  private String whereIn(List<?> list, String whereColumn) {
    return list.stream().map(this::toSql)
        .collect(Collectors.joining(", ", String.format("WHERE %s IN (", whereColumn), ")"));
  }

  private String toSql(Object data) {
    if (data instanceof Long || data instanceof Integer) {
      return String.valueOf(data);
    } else if (data instanceof UUID uuidData) {
      return String.format("UNHEX(REPLACE('%s', '-', ''))", uuidData);
    } else {
      throw new UnsupportedOperationException(
          String.format("Provided list Type: '%s' is not yet supported", data.getClass().getSimpleName()));
    }
  }

}
