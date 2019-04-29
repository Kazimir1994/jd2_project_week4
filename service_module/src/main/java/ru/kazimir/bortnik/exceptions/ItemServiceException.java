package ru.kazimir.bortnik.exceptions;

public class ItemServiceException extends RuntimeException {
    public ItemServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

