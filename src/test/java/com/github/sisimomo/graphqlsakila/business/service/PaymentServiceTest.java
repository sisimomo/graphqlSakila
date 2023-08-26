package com.github.sisimomo.graphqlsakila.business.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.business.dao.entity.PaymentEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.RentalEntity;
import com.github.sisimomo.graphqlsakila.business.dao.entity.StaffMemberEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.entity.CustomerEntity;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.PaymentCreate;
import com.github.sisimomo.graphqlsakila.seed.TestCreateSeedData;
import com.github.sisimomo.graphqlsakila.test.TestUtils;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Service
public class PaymentServiceTest {

  @Autowired
  private PaymentService service;

  public List<PaymentEntity> seed(@NotEmpty List<@NotNull RentalEntity> rentalEntities) {
    return service.createAll(rentalEntities.stream().map(rentalEntity -> {
      BigDecimal rentalDurationInDays = new BigDecimal(
          Duration.between(rentalEntity.getRentalDate(), rentalEntity.getReturnDate()).get(ChronoUnit.SECONDS))
          .divide(new BigDecimal(86400), 0, RoundingMode.UP);
      BigDecimal amount = rentalDurationInDays
          .divide(new BigDecimal(rentalEntity.getFilmCopy().getFilm().getRentalDuration()), 0, RoundingMode.UP)
          .multiply(rentalEntity.getFilmCopy().getFilm().getReplacementCost(), new MathContext(2, RoundingMode.UP));
      return PaymentCreate.newBuilder().staffMemberUuid(rentalEntity.getStaffMember().getUuid())
          .customerUuid(rentalEntity.getCustomer().getUuid()).rentalUuid(rentalEntity.getUuid()).amount(amount)
          .paymentDate(rentalEntity.getReturnDate()).build();
    }).toList());
  }

  public List<PaymentEntity> seed(
      @NotEmpty Collection<@NotNull Pair<@NotNull StaffMemberEntity, @NotNull CustomerEntity>> pairs) {
    return service.createAll(pairs.stream()
        .map(pair -> PaymentCreate.newBuilder().staffMemberUuid(pair.getFirst().getUuid())
            .customerUuid(pair.getSecond().getUuid())
            .amount(TestUtils.randomFloat(TestCreateSeedData.PAYMENT_WITHOUT_RENTAL_AMOUNT, 2))
            .paymentDate(TestUtils.randomInstant(TestCreateSeedData.PAYMENT_WITHOUT_RENTAL_PAYMENT_DATE)).build())
        .toList());
  }

}
