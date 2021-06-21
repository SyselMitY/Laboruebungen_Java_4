package com.example.gutegadsen_backend.exception;

import lombok.Getter;

@Getter
public class TagNotFoundException extends Exception {
    private final String tagName;
    public TagNotFoundException(String tagName) {
        super("Tag with name " + tagName + " not found");
        this.tagName = tagName;
    }
}
