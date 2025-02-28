package com.pretallez.common.exception;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Void>> handleGenericException(Exception exception) {

        log.error("Unhandled exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(500)
                .body(CustomApiResponse.ERROR(ResErrorCode.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        log.error("DataIntegrityViolation exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(CustomApiResponse.ERROR(ResErrorCode.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException exception) {

        log.error("EntityNotFoundException exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(CustomApiResponse.ERROR(ResErrorCode.NOT_FOUND, exception.getMessage()));
    }
}
