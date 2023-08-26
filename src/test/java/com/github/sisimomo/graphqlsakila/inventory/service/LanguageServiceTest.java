package com.github.sisimomo.graphqlsakila.inventory.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.LanguageRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.LanguageEntity;

import jakarta.validation.constraints.NotNull;

@Service
public class LanguageServiceTest {

  @Autowired
  private LanguageService service;

  public LanguageEntity seed(@NotNull String name) {
    LanguageRequest languageCreate = LanguageRequest.newBuilder().name(name).build();
    return service.create(languageCreate);
  }

}
