package cf.soisi.rest_socialmedia;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CentralExceptionHandler {

    //Handl de Exceptions los gehts
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleElementNotFound(NoSuchElementException ex) {
        return ex.getMessage();
    }

}
