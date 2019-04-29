package ru.kazimir.bortnik.exceptions;

public class ItemRepositoryException extends RuntimeException {
    public ItemRepositoryException(String message,Throwable throwable) {
        super(throwable);
    }
}

