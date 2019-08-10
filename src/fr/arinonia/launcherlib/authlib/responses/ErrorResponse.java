package fr.arinonia.launcherlib.authlib.responses;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ErrorResponse implements Serializable {

  private String error;
  private String errorMessage;
  private String cause;

  public String getError() {
    return error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getCause() {
    return cause;
  }

}
