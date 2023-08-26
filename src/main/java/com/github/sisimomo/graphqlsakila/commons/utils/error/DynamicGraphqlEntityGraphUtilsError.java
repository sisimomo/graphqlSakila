package com.github.sisimomo.graphqlsakila.commons.utils.error;


import com.github.sisimomo.graphqlsakila.commons.service.error.Error;
import com.netflix.graphql.types.errors.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DynamicGraphqlEntityGraphUtilsError implements Error {

  MISSING_ANNOTATION(ErrorType.INTERNAL, "FATAL ERROR",
      "Annotation \"%s\" was not found on class \"%s\"", "498VD6");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  DynamicGraphqlEntityGraphUtilsError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
