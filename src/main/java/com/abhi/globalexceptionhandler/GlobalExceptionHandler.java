package com.abhi.globalexceptionhandler;

import CustomizedExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(AuthenticationErrorException.class)
    public ResponseEntity<CustomExceptionResponse> unAuthenticated(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "401:UnAuthenticated");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleOrderNotFound(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "404:Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(org.hibernate.LazyInitializationException.class)
    public ResponseEntity<Object> handleLazyInitializationException(org.hibernate.LazyInitializationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Data loading failed: " + ex.getMessage());
        // You return 500 or 400 here, which prevents the internal /error redirect
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> receiptNotFound(Exception e) {
        System.out.println("Exception Object Name :: " + e.getClass().getName());
        CustomExceptionResponse errorResponse = new CustomExceptionResponse(e.getMessage(), LocalDateTime.now(), "404:Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
