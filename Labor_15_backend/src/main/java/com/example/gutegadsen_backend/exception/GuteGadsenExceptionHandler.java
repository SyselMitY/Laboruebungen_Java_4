package com.example.gutegadsen_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GuteGadsenExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserNotFoundException handleUserNotFound(UserNotFoundException e) {
        return e;
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public PostNotFoundException handlePostNotFound(PostNotFoundException e) {
        return e;
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ImageNotFoundException handlePostNotFound(ImageNotFoundException e) {
        return e;
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TagNotFoundException handleTagNotFound(TagNotFoundException e) {
        return e;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserAlreadyExistsException handleTagNotFound(UserAlreadyExistsException e) {
        return e;
    }


}
