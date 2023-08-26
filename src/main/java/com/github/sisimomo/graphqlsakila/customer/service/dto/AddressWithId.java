package com.github.sisimomo.graphqlsakila.customer.service.dto;

import com.github.sisimomo.graphqlsakila.dgscodegen.types.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Delegate;

@Builder
public class AddressWithId extends Address {

  @Getter
  private Long id;

  @Delegate
  private Address innerAddress;

}
