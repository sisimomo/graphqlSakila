package com.github.sisimomo.graphqlsakila.inventory.service.mapper;

import org.mapstruct.Mapper;

import com.github.sisimomo.graphqlsakila.commons.service.mapper.BaseCruMapper;
import com.github.sisimomo.graphqlsakila.commons.service.mapper.CentralJpaEntityMapperConfig;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.Category;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CategoryRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.batchloaderprojection.CategoryBatchLoaderProjection;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;


@Mapper(config = CentralJpaEntityMapperConfig.class)
public interface CategoryMapper extends BaseCruMapper<CategoryEntity, Category, CategoryRequest, CategoryRequest> {

  CategoryEntity projectionToDao(CategoryBatchLoaderProjection projection);

}
