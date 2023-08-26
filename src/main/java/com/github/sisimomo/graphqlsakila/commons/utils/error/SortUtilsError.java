package com.github.sisimomo.graphqlsakila.commons.utils.error;


import com.github.sisimomo.graphqlsakila.commons.service.error.Error;
import com.netflix.graphql.types.errors.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortUtilsError implements Error {

  NOT_FOUND_BY_DTO_PATH(ErrorType.BAD_REQUEST,
      "Could not found corresponding field for path \"%s\"", "G5549Q");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  SortUtilsError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
