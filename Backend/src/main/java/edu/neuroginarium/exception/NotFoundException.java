package edu.neuroginarium.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> clazz, Long id) {
        super(clazz.getName() + " not found for id: " + id);
    }

    public NotFoundException(Class<?> clazz, String email) {
        super(clazz.getName() + " not found for email: " + email);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
