package com.verse.user.exception;

/**
 * Exception thrown when a user profile is not found.
 */
public class UserProfileNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserProfileNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserProfileNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserProfileNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public UserProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
