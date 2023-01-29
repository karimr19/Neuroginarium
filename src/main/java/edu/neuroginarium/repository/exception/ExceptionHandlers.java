package edu.neuroginarium.repository.exception;

import edu.neuroginarium.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(NotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(new Error(status.toString(), ex.getMessage()));
    }

    @ExceptionHandler(EmailConfirmationFailedException.class)
    public ResponseEntity<Error> handleEmailConfirmationFailedException(EmailConfirmationFailedException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(new Error(status.toString(), ex.getMessage()));
    }

    @ExceptionHandler(PlayersCntIsMaxException.class)
    public ResponseEntity<Error> handlePlayersCntIsMaxException(PlayersCntIsMaxException ex) {
        var status = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status).body(new Error(status.toString(), ex.getMessage()));
    }
}
