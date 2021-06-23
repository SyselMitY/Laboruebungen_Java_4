package com.example.gutegadsen_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginRequestBody {
    private final String username;
    private final String password;
}
