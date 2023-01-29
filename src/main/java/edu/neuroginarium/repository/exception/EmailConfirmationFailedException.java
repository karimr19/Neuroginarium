package edu.neuroginarium.repository.exception;

public class EmailConfirmationFailedException extends RuntimeException {
    public EmailConfirmationFailedException(String email) {
        super("token is incorrect for email: " + email);
    }
}
