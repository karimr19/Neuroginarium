package edu.neuroginarium.exception;

public class PlayersCntIsMaxException extends RuntimeException {
    public PlayersCntIsMaxException(String token) {
        super("Players count is maximal for game with token = : " + token);
    }
}
