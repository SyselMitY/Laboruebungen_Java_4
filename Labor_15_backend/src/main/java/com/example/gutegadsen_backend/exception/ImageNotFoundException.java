package com.example.gutegadsen_backend.exception;

import lombok.Getter;

@Getter
public class ImageNotFoundException extends Exception {
    private final Long imageId;
    public ImageNotFoundException(Long imageId) {
        super("Image with id " + imageId + " not found");
        this.imageId = imageId;
    }
}
