package edu.neuroginarium.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Error {
    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    @NotNull
    private String message;
}
