package com.pretallez.common.handler;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.enums.error.ResErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Void>> handleGenericException(Exception exception) {

        log.error("Unhandled exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(500)
                .body(CustomApiResponse.ERROR(ResErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        log.error("DataIntegrityViolation exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(HttpStatus.CONFLICT.value())
                .body(CustomApiResponse.ERROR(ResErrorCode.CONFLICT));
    }
}
