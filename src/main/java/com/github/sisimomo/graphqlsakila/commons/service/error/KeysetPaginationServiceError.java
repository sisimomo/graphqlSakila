package com.github.sisimomo.graphqlsakila.commons.service.error;


import com.netflix.graphql.types.errors.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KeysetPaginationServiceError implements Error {
  NOT_FOUND_BY_DTO_PATH(ErrorType.BAD_REQUEST, "Could not found corresponding field for path \"%s\"", "G5549Q"),
  BAD_FILTER(ErrorType.BAD_REQUEST, "Could not process provided filter \"%s\"", "6CXQ64"),
  FIELD_DOES_NOT_ALLOW_FILTERING(ErrorType.BAD_REQUEST, "The field at path \"%s\" doesn't allow filtering", "OTU3H8"),
  FIELD_DOES_NOT_ALLOW_SORTING(ErrorType.BAD_REQUEST, "The field at path \"%s\" doesn't allow sorting", "TU9FKH"),
  SYSTEM_ERROR(ErrorType.INTERNAL, "System error. Please try again later", "E8J7F0");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  KeysetPaginationServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
