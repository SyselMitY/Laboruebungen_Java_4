package com.example.gutegadsen_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreationRequestBody {
    private final String username;
    private final String imageDataString;
}
