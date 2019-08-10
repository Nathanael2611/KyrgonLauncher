package fr.arinonia.launcherlib.authlib.exceptions;

import fr.arinonia.launcherlib.authlib.responses.ErrorResponse;

@SuppressWarnings("serial")
public class UserMigratedException extends RequestException {

  public UserMigratedException(ErrorResponse error) {
    super(error);
  }

}
