package com.github.sisimomo.graphqlsakila.commons.dao.repository;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public interface WhereInAndOrderByCaseRepository<T> {

  List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, String inColumn, List<?> inList,
      String orderColumn, List<?> orderList, @NotNull Class<T> clazz);

  List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, String inAndOrderColumn,
      @NotNull List<?> inAndOrderList, @NotNull Class<T> clazz);

  List<T> findByColumnInAndOrderByCase(@NotNull String baseSqlQuery, @NotNull List<?> inAndOrderList,
      @NotNull Class<T> clazz);

  List<T> findOrderByCase(@NotNull String baseSqlQuery, String orderColumn, @NotNull List<?> orderList,
      @NotNull Class<T> clazz);

}
