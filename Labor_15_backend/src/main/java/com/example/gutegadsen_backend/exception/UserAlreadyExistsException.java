package com.example.gutegadsen_backend.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends Exception {
    private final String username;
    public UserAlreadyExistsException(String username) {
        super("User \"" + username + "\" already exists");
        this.username = username;
    }
}
