package ru.kazimir.bortnik.exceptions;

public class DriveDataBaseException extends RuntimeException {
    public DriveDataBaseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
