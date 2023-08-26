package com.github.sisimomo.graphqlsakila.inventory.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.RatingRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.RatingEntity;

import jakarta.validation.constraints.NotNull;

@Service
public class RatingServiceTest {

  @Autowired
  private RatingService service;

  public RatingEntity seed(@NotNull String name) {
    RatingRequest ratingCreate = RatingRequest.newBuilder().name(name).build();
    return service.create(ratingCreate);
  }

}
