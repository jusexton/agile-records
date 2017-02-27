package main.java.database;

/**
 * Thrown when a login attempt is failed.
 */
public class FailedLoginException extends Exception{
    public FailedLoginException(String message) {
        super(message);
    }
}
