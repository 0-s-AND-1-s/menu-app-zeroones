package com.abhi.globalexceptionhandler;

import CustomizedExceptions.CustomExceptionResponse;
import CustomizedExceptions.MenuItemNotFoundException;
import CustomizedExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleItemNotFound(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "404:Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionResponse> accessDenied(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "403:Forbidden");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> userNotAvailable(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "404:Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
