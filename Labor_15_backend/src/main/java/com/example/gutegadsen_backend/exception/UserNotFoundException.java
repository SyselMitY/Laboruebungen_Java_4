package com.example.gutegadsen_backend.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private final String username;
    public UserNotFoundException(String username) {
        super("User \"" + username + "\" not found");
        this.username = username;
    }
}
