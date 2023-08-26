package com.github.sisimomo.graphqlsakila.business.service.error;

import com.netflix.graphql.types.errors.ErrorType;
import com.github.sisimomo.graphqlsakila.commons.service.error.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Payment [UUID: %s] not found", "45QB87"),
  STAFF_MEMBER_MUST_BE_ACTIVE(ErrorType.BAD_REQUEST, "Staff Member [UUID: %s] must be active to be link to a Payment",
      "Q1LZN7"),
  CUSTOMER_MUST_BE_ACTIVE(ErrorType.BAD_REQUEST, "Customer [UUID: %s] must be active to be link to a Payment",
      "OF0JFS");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  PaymentServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
