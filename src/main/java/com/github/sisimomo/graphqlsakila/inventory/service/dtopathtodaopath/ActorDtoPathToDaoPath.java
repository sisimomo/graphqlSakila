package com.github.sisimomo.graphqlsakila.inventory.service.dtopathtodaopath;

import com.github.sisimomo.graphqlsakila.commons.service.sort.DtoPathToDaoPath;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity_;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActorDtoPathToDaoPath implements DtoPathToDaoPath {
  UUID(DgsConstants.ACTOR.Uuid, ActorEntity_.UUID, true, true),
  CREATE_DATE(DgsConstants.ACTOR.CreateDate, ActorEntity_.CREATE_DATE, true, true),
  UPDATE_DATE(DgsConstants.ACTOR.UpdateDate, ActorEntity_.UPDATE_DATE, true, true),
  FIRST_NAME(DgsConstants.ACTOR.FirstName, ActorEntity_.FIRST_NAME, true, true),
  LAST_NAME(DgsConstants.ACTOR.LastName, ActorEntity_.LAST_NAME, true, true);

  private final String dtoPath;
  private final String daoPath;
  private final Class<? extends DtoPathToDaoPath> nested;
  private final boolean sortAllowed;
  private final boolean filterAllowed;

  ActorDtoPathToDaoPath(String dtoPath, String daoPath, boolean isSortAllowed, boolean isFilterAllowed) {
    this(dtoPath, daoPath, null, isSortAllowed, isFilterAllowed);
  }

}
