package com.github.sisimomo.graphqlsakila.customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sisimomo.graphqlsakila.customer.dao.entity.CountryEntity;
import com.github.sisimomo.graphqlsakila.customer.dao.repository.CountryRepository;
import com.github.sisimomo.graphqlsakila.dgscodegen.types.CountryRequest;

import jakarta.validation.constraints.NotNull;

@Service
public class CountryServiceTest {

  @Autowired
  private CountryService service;

  @Autowired
  private CountryRepository countryRepository;

  public CountryEntity getOrCreate(@NotNull String country) {
    Optional<CountryEntity> oEntity = countryRepository.findByCountry(country);
    return oEntity.orElseGet(() -> service.create(CountryRequest.newBuilder().country(country).build()));
  }

}
