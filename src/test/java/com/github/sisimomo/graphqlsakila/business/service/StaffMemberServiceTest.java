package com.github.sisimomo.graphqlsakila.business.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.customer.service.AddressServiceTest;
import com.github.sisimomo.graphqlsakila.customer.service.CityServiceTest;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.StaffMemberCreate;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotNull;

@Service
public class StaffMemberServiceTest {

  @Autowired
  private StaffMemberService service;

  @Autowired
  private CityServiceTest cityServiceTest;

  @Autowired
  private AddressServiceTest addressServiceTest;

  public List<StaffMemberEntity> seed(@NotNull List<UUID> storeUuids, @NotNull List<MockMultipartFile> staffPictures,
      int number) {
    return service.createAll(IntStream.range(0, number).boxed()
        .map(i -> StaffMemberCreate.newBuilder()
            .username(TestUtils.trimStringLength(TestUtils.getFaker().name().username(), 16)).password("QLAMVTcyuwif1!")
            .firstName(TestUtils.getFaker().name().firstName()).lastName(TestUtils.getFaker().name().lastName())
            .storeUuid(TestUtils.getRandomItem(storeUuids)).picture(TestUtils.getRandomItem(staffPictures))
            .email(TestUtils.getFaker().internet().emailAddress()).address(addressServiceTest.seed()).build())
        .toList());
  }

}
