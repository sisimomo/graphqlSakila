package com.github.sisimomo.graphqlsakila.commons.service.error;

import com.netflix.graphql.types.errors.ErrorType;

public interface Error {

  ErrorType getErrorType();

  String getErrorMessage();

  String getSystemErrorMessage();

  String getErrorId();

}

