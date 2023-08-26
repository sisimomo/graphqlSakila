package com.github.sisimomo.graphqlsakila.customer.service.error;

import com.github.sisimomo.graphqlsakila.commons.service.error.Error;
import com.netflix.graphql.types.errors.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Customer [UUID: %s] not found", "LWXND9");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  CustomerServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
