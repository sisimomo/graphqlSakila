package com.github.sisimomo.graphqlsakila.inventory.service.error;

import com.github.sisimomo.graphqlsakila.commons.service.error.Error;
import com.netflix.graphql.types.errors.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpecialFeatureServiceError implements Error {
  NOT_FOUND_BY_UUID(ErrorType.NOT_FOUND, "Rating [UUID: %s] not found", "G0996G");

  private final ErrorType errorType;
  private final String errorMessage;
  private final String systemErrorMessage;
  private final String errorId;

  SpecialFeatureServiceError(ErrorType errorType, String errorMessage, String errorId) {
    this(errorType, errorMessage, errorMessage, errorId);
  }

}
