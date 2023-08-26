package com.github.sisimomo.graphqlsakila.customer.service;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.AddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.AddressEntity;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

@Service
public class AddressServiceTest {

  @Autowired
  private AddressService service;

  @Autowired
  private CityServiceTest cityServiceTest;

  public AddressRequest seed() {
    return AddressRequest.newBuilder().address(TestUtils.getFaker().address().streetAddress())
        .address2(TestUtils.getFaker().address().secondaryAddress())
        .district(TestUtils.getFaker().address().cityPrefix()).phone(TestUtils.getFaker().phoneNumber().phoneNumber())
        .postalCode(TestUtils.getFaker().address().zipCode())
        .cityUuid(cityServiceTest.getOrCreate(TestUtils.trimStringLength(TestUtils.getFaker().address().city(), 50),
            TestUtils.trimStringLength(TestUtils.getFaker().address().country(), 50)).getUuid())
        .build();
  }

  public AddressRequest convertToRequestDTO(AddressEntity entity) {
    return AddressRequest.newBuilder().address(entity.getAddress()).address2(entity.getAddress2())
        .district(entity.getDistrict()).phone(entity.getPhone()).postalCode(entity.getPostalCode())
        .cityUuid(entity.getCity().getUuid()).build();
  }

}
