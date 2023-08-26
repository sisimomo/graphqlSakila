package com.github.sisimomo.graphqlsakila.customer.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CustomerCreate;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotNull;

@Service
public class CustomerServiceTest {

  @Autowired
  private CustomerService service;

  @Autowired
  private CityServiceTest cityServiceTest;

  @Autowired
  private AddressServiceTest addressServiceTest;

  public List<CustomerEntity> seed(@NotNull List<UUID> storeUuids, int number) {
    return service.createAll(IntStream.range(0, number).boxed()
        .map(i -> CustomerCreate.newBuilder().firstName(TestUtils.getFaker().name().firstName())
            .lastName(TestUtils.getFaker().name().lastName()).storeUuid(TestUtils.getRandomItem(storeUuids))
            .email(TestUtils.getFaker().internet().emailAddress()).address(addressServiceTest.seed()).build())
        .toList());
  }

}
