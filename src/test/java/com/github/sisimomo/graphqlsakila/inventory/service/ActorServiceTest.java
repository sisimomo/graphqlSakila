package com.github.sisimomo.graphqlsakila.inventory.service;


import com.github.sisimomo.graphqlsakila.dgscodegen.types.ActorRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.ActorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.test.TestUtils;

@Service
public class ActorServiceTest {

  @Autowired
  private ActorService service;

  public ActorEntity seed() {
    ActorRequest actorCreate = ActorRequest.newBuilder().firstName(TestUtils.getFaker().name().firstName())
        .lastName(TestUtils.getFaker().name().lastName()).build();
    return service.create(actorCreate);
  }

}
