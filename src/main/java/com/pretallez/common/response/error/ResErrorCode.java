package com.pretallez.common.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResErrorCode implements ResCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "Invalid Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, 40300, "Access Denied"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "Not Found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 40500, "Method Not Allowed"),
    CONFLICT(HttpStatus.CONFLICT, 40900, "Conflict Occurred"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Internal Server Error"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 50300, "Service Unavailable"),
    API_CALL_FAILED(HttpStatus.SERVICE_UNAVAILABLE, 50301, "API Call Failed");

    private final HttpStatusCode httpStatusCode;
    private final Integer code;
    private final String description;
}

