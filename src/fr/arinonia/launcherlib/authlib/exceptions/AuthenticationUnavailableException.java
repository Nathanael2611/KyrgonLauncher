package fr.arinonia.launcherlib.authlib.exceptions;

import fr.arinonia.launcherlib.authlib.responses.ErrorResponse;

@SuppressWarnings("serial")
public class AuthenticationUnavailableException extends Exception {

  public AuthenticationUnavailableException(ErrorResponse error) {

  }

}
