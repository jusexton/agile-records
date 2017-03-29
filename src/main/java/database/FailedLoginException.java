package database;

/**
 * Thrown when a login attempt is failed.
 */
public class FailedLoginException extends RuntimeException {
    public FailedLoginException(String message) {
        super(message);
    }
}
