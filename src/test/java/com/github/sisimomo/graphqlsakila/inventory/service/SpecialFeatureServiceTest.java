package com.github.sisimomo.graphqlsakila.inventory.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.SpecialFeatureRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.SpecialFeatureEntity;

import jakarta.validation.constraints.NotNull;

@Service
public class SpecialFeatureServiceTest {

  @Autowired
  private SpecialFeatureService service;

  public SpecialFeatureEntity seed(@NotNull String name) {
    SpecialFeatureRequest ratingCreate = SpecialFeatureRequest.newBuilder().name(name).build();
    return service.create(ratingCreate);
  }

}
