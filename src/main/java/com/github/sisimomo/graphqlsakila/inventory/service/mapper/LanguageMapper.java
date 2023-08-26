package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Language;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.LanguageRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;
import org.mapstruct.Mapper;


@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface LanguageMapper extends BaseCruMapper<LanguageEntity, Language, LanguageRequest, LanguageRequest> {
}
