package fr.arinonia.launcherlib.launchlib.exceptions;

public class ErrorException extends RuntimeException
{
    /**
     * Normal constructor
     *
     * @param message The message
     */
    public ErrorException(String message)
    {
        super("Ups ! Looks like you failed : " + message);
    }

    /**
     * Constructor with a cause
     *
     * @param message The message
     * @param cause   The cause
     */
    public ErrorException(String message, Throwable cause)
    {
        super("Ups ! Looks like you failed : " + message, cause);
    }
}