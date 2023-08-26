package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StoreEntity;
import com.github.sisimomo.graphqlsakila.business.service.StoreService;
import com.github.sisimomo.graphqlsakila.business.service.mapper.StoreMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.DgsConstants;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopy;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.FilmCopyCreate;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmEntity;
import com.github.sisimomo.graphqlsakila.inventory.service.FilmService;

@Mapper(config = CentralJpaEntityMapperConfig.class, uses = {FilmMapper.class, StoreMapper.class})
public abstract class FilmCopyMapper implements BaseCruMapper<FilmCopyEntity, FilmCopy, FilmCopyCreate, Void> {

  @Autowired
  protected FilmService filmService;

  @Autowired
  protected StoreService storeService;

  @Override
  @Mapping(target = "film", source = DgsConstants.FILMCOPYCREATE.FilmUuid,
      qualifiedByName = "FilmCopyMapper#filmUuidToFilmEntity")
  @Mapping(target = "store", source = DgsConstants.FILMCOPYCREATE.StoreUuid,
      qualifiedByName = "FilmCopyMapper#storeUuidToStoreEntity")
  @Mapping(target = "updateDate", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "uuid", ignore = true)
  @Mapping(target = "id", ignore = true)
  public abstract FilmCopyEntity convertToDao(FilmCopyCreate createDto);

  @Override
  public void update(FilmCopyEntity entity, Void updateDto) {
    throw new UnsupportedOperationException();
  }


  @Named("FilmCopyMapper#filmUuidToFilmEntity")
  protected FilmEntity filmUuidToFilmEntity(UUID uuid) {
    return uuid != null ? filmService.getByUid(uuid) : null;
  }

  @Named("FilmCopyMapper#storeUuidToStoreEntity")
  protected StoreEntity storeUuidToStoreEntity(UUID uuid) {
    return uuid != null ? storeService.getByUid(uuid) : null;
  }

}
