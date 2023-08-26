package com.github.sisimomo.graphqlsakila.business.service.error;

import com.netflix.graphql.types.errors.ErrorType;
import com.github.sisimomo.graphqlsakila.commons.service.error.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StoreServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Store [UUID: %s] not found", "UBW8YM"),
  STAFF_MEMBER_MUST_BE_ACTIVE(ErrorType.BAD_REQUEST, "Staff Member [UUID: %s] must be active to be link to a Store",
      "7FG6Y5");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  StoreServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
