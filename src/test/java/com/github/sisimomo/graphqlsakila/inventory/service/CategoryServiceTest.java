package com.github.sisimomo.graphqlsakila.inventory.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.CategoryRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.CategoryEntity;

import jakarta.validation.constraints.NotNull;

@Service
public class CategoryServiceTest {

  @Autowired
  private CategoryService service;

  public CategoryEntity seed(@NotNull String name) {
    CategoryRequest categoryCreate = CategoryRequest.newBuilder().name(name).build();
    return service.create(categoryCreate);
  }

}
