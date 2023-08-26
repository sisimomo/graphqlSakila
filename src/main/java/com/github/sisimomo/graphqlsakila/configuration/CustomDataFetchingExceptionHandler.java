package com.github.sisimomo.graphqlsakila.configuration;

import com.github.sisimomo.graphqlsakila.commons.service.exception.UncheckedException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.netflix.graphql.types.errors.TypedGraphQLError;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {

  @Override
  public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    if (handlerParameters.getException() instanceof UncheckedException exception) {
      Map<String, Object> debugInfo = new HashMap<>();
      debugInfo.put("errorId", exception.getError().getErrorId());
      debugInfo.put("requestUuid", MDC.get(Slf4jMDCFilter.DEFAULT_MDC_REQUEST_UUID_KEY));

      GraphQLError graphqlError = TypedGraphQLError.newInternalErrorBuilder()
          .message(exception.getFormattedUserErrorMsg()).errorType(exception.getError().getErrorType())
          .debugInfo(debugInfo).path(handlerParameters.getPath()).build();

      DataFetcherExceptionHandlerResult result =
          DataFetcherExceptionHandlerResult.newResult().error(graphqlError).build();

      return CompletableFuture.completedFuture(result);
    } else {
      return DataFetcherExceptionHandler.super.handleException(handlerParameters);
    }
  }

}
