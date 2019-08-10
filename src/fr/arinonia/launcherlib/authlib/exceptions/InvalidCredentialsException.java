package fr.arinonia.launcherlib.authlib.exceptions;

import fr.arinonia.launcherlib.authlib.responses.ErrorResponse;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends RequestException {

  public InvalidCredentialsException(ErrorResponse error) {
    super(error);
  }

}
