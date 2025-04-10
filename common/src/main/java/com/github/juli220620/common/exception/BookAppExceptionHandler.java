package com.github.juli220620.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestControllerAdvice
public class BookAppExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(BAD_REQUEST)
        public String handleInvalidImageRequest(RuntimeException e) {
            return e.getMessage();
        }

        @ExceptionHandler(NoSuchElementException.class)
        @ResponseStatus(NO_CONTENT)
        public void handleNoElementExists() {}

}
