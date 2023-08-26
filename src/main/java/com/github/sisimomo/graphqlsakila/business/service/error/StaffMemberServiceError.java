package com.github.sisimomo.graphqlsakila.business.service.error;

import com.netflix.graphql.types.errors.ErrorType;
import com.github.sisimomo.graphqlsakila.commons.service.error.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StaffMemberServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Staff Member [UUID: %s] not found", "3S0X9P"),
  PASSWORD_TOO_WEAK(ErrorType.BAD_REQUEST, "Staff Member Password is too weak %s", "7UR84M");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  StaffMemberServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
