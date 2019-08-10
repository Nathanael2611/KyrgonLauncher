package fr.arinonia.launcherlib.authlib.exceptions;

import fr.arinonia.launcherlib.authlib.responses.ErrorResponse;

@SuppressWarnings("serial")
public class RequestException extends Exception {

  private ErrorResponse error;

  public RequestException(ErrorResponse error) {
    this.error = error;
  }

  public ErrorResponse getResponse() {
    return this.error;
  }

  public String getError() {
    return this.error.getError();
  }

  public String getErrorMessage() {
    return this.error.getErrorMessage();
  }

  public String getErrorCause() {
    return this.error.getCause();
  }

}
