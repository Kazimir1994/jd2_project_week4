package ru.kazimir.bortnik.exceptions;

public class InitializationDataBseException extends RuntimeException {
    public InitializationDataBseException(String message) {
        super(message);
    }

    public InitializationDataBseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
