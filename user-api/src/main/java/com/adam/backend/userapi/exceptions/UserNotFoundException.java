package com.adam.backend.userapi.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("Usuário não encontrado");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
