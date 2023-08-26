package com.github.sisimomo.graphqlsakila.business.service;

import java.time.Instant;
import java.util.List;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.RentalRequest;
import com.github.sisimomo.graphqlsakila.inventory.dao.entity.FilmCopyEntity;
import com.github.sisimomo.graphqlsakila.seed.TestCreateSeedData;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotNull;

@Service
public class RentalServiceTest {

  @Autowired
  private RentalService service;

  public List<RentalEntity> seed(
      List<@NotNull Triplet<@NotNull StaffMemberEntity, @NotNull CustomerEntity, @NotNull FilmCopyEntity>> triplets) {
    return service.createAll(triplets.stream().map(triplet -> {
      Instant rentalDate = TestUtils.randomInstant(TestCreateSeedData.RENTAL_DATE);
      Instant returnDate = rentalDate.plus(TestUtils.randomDuration(TestCreateSeedData.RENTAL_DURATION));
      return RentalRequest.newBuilder().staffMemberUuid(triplet.getValue0().getUuid())
          .customerUuid(triplet.getValue1().getUuid()).filmCopyUuid(triplet.getValue2().getUuid())
          .rentalDate(rentalDate).returnDate(returnDate.isBefore(Instant.now()) ? returnDate : null).build();
    }).toList());
  }

}
