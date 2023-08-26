package com.github.sisimomo.graphqlsakila.commons.service.sort;

public interface DtoPathToDaoPath {

  String getDtoPath();

  String getDaoPath();

  Class<? extends DtoPathToDaoPath> getNested();

  boolean isSortAllowed();

  boolean isFilterAllowed();

}
