package ru.kazimir.bortnik.exceptions;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(String message, Throwable throwable) {
        super(throwable);
    }
}
