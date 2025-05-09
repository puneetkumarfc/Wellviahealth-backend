package com.wellvia.WellviaHealth.exception;

public class SpecializationException extends RuntimeException {
    public SpecializationException(String message) {
        super(message);
    }

    public SpecializationException(String message, Throwable cause) {
        super(message, cause);
    }
} 