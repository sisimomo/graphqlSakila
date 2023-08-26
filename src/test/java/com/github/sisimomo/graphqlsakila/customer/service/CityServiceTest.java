package com.github.sisimomo.graphqlsakila.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CityEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.CityRepository;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CityRequest;

import jakarta.validation.constraints.NotNull;

@Service
public class CityServiceTest {

  @Autowired
  private CityService service;

  @Autowired
  private CountryServiceTest countryServiceTest;

  @Autowired
  private CityRepository cityRepository;

  public CityEntity getOrCreate(@NotNull String city, @NotNull String country) {
    Optional<CityEntity> oEntity = cityRepository.findByCityAndCountry_Country(city, country);
    return oEntity.orElseGet(() -> service.create(
        CityRequest.newBuilder().city(city).countryUuid(countryServiceTest.getOrCreate(country).getUuid()).build()));
  }

}
