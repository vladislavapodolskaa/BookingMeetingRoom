package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        logger.error("Validation failed: {}", exception.getMessage());
        return new ResponseEntity<>(buildError(HttpStatus.BAD_REQUEST, exception, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        logger.error("Resource not found: {}", exception.getMessage());
        return new ResponseEntity<>(buildError(HttpStatus.NOT_FOUND, exception, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> handleIllegalStateException(IllegalStateException exception, WebRequest request) {
        logger.error("State conflict: {}", exception.getMessage());
        return new ResponseEntity<>(buildError(HttpStatus.CONFLICT, exception, request), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception exception, WebRequest request) {
        logger.error("CRITICAL: Unexpected server error: {}", exception.getMessage());
        return new ResponseEntity<>(buildError(HttpStatus.INTERNAL_SERVER_ERROR, exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessage buildError(HttpStatus status, Exception exception, WebRequest request) {
        return new ErrorMessage(
                status.value(),
                LocalDateTime.now(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }
}
