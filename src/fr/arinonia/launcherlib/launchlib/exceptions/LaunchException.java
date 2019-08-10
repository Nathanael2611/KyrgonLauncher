package fr.arinonia.launcherlib.launchlib.exceptions;

import java.io.IOException;

import fr.arinonia.launcherlib.authlib.responses.ErrorResponse;

@SuppressWarnings("serial")
public class LaunchException extends Exception {
	private ErrorResponse error;


	public LaunchException(String string, IOException e) {
		// TODO Auto-generated constructor stub
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
