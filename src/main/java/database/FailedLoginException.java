package main.java.database;

/**
 * Thrown in the scenario of a bad login attempt.
 */
public class FailedLoginException extends Exception {
    public FailedLoginException(String message) {
        super(message);
    }
}
