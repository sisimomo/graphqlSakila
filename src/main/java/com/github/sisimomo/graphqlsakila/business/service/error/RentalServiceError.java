package com.github.sisimomo.graphqlsakila.business.service.error;

import com.netflix.graphql.types.errors.ErrorType;
import com.github.sisimomo.graphqlsakila.commons.service.error.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RentalServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Rental [UUID: %s] not found", "B3IR1T"),
  STAFF_MEMBER_MUST_BE_ACTIVE(ErrorType.BAD_REQUEST, "Staff Member [UUID: %s] must be active to be link to a Rental",
      "LN9NTL"),
  CUSTOMER_MUST_BE_ACTIVE(ErrorType.BAD_REQUEST, "Customer [UUID: %s] must be active to be link to a Rental", "S1JIB1");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  RentalServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
