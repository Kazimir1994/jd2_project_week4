package ru.kazimir.bortnik.exceptions;

public class ConnectionDataBaseExceptions extends RuntimeException {
    public ConnectionDataBaseExceptions(String message, Throwable throwable) {
        super(message, throwable);
    }
}
