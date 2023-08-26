package com.github.sisimomo.graphqlsakila.commons.service.mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface BaseCruMapper<EntityT, DtoT, CreateDtoT, UpdateDtoT> {

  List<DtoT> convertToDto(List<EntityT> entities);

  DtoT convertToDto(EntityT entity);

  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  EntityT convertToDao(CreateDtoT createDto);

  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  void update(@MappingTarget EntityT entity, UpdateDtoT updateDto);

}
