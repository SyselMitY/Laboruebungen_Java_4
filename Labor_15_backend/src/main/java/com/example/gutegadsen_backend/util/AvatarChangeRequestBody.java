package com.example.gutegadsen_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class AvatarChangeRequestBody {
    @NotNull
    private final String username;
    @NotNull
    private final String imageDataString;
}
