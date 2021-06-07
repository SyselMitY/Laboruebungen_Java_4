package com.example.gutegadsen_backend.exception;

import lombok.Getter;

@Getter
public class PostNotFoundException extends Exception {
    private Long postId;
    public PostNotFoundException(Long postId) {
        super("Post with id " + postId + " not found");
        this.postId = postId;
    }
}
