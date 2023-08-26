package com.github.sisimomo.graphqlsakila.commons.service.exception;


import java.util.function.Consumer;

import com.github.sisimomo.graphqlsakila.commons.service.error.Error;

import lombok.Getter;

@Getter
public class UncheckedException extends RuntimeException {

  private final transient Error error;
  private final String userMsg;

  public UncheckedException(Error error, Consumer<String> log, Object... msgParams) {
    this(error, log, null, msgParams);
  }

  public UncheckedException(Error error, Consumer<String> log, Throwable cause, Object... msgParams) {
    super(String.format("[Error type: %s]: %s", error.getErrorType().toString(),
        (msgParams.length == 0 ? error.getSystemErrorMessage()
            : String.format(error.getSystemErrorMessage(), msgParams))),
        cause);

    if (log != null) {
      log.accept(getMessage());
    }

    this.error = error;
    userMsg = (msgParams.length == 0 ? error.getErrorMessage() : String.format(error.getErrorMessage(), msgParams));
  }

  public String getFormattedUserErrorMsg() {
    return userMsg;
  }

}
