package org.example.user;

public class NotValidMailException extends Exception {
    public NotValidMailException() {
        super("Invalid email address.");
    }

    public NotValidMailException(String message) {
        super(message);
    }
}
