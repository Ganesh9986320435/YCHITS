package com.Ychits.exceptions;

import com.Ychits.dao.ApiResponse;
import com.Ychits.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            atomicInteger.getAndIncrement();
            String errorNumber = "Error " + atomicInteger.get();
            String errorMessage = error.getDefaultMessage();
            errors.put(errorNumber, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        String errorNumber = "Error 1";
        String errorMessage = ex.getMessage();
        errors.put(errorNumber, errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        ex.getConstraintViolations().forEach(error -> {
            atomicInteger.getAndIncrement();
            String errorNumber = "Error " + atomicInteger.get();
            String errorMessage = error.getMessage();
            errors.put(errorNumber, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseUtil.getAlreadyExistResponse(ex.getMessage());
    }
}
