package com.pretallez.common.response.success;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResSuccessCode implements ResCode {
    SUCCESS(HttpStatus.OK,  20000,"Success"),
    READ(HttpStatus.OK, 20001, "Read Successfully"),
    UPDATED(HttpStatus.OK, 20002, "Updated Successfully"),
    DELETED(HttpStatus.OK, 20003, "Deleted Successfully"),
    SAVED(HttpStatus.OK, 20004, "Data saved successfully"),
    CREATED(HttpStatus.OK, 20100, "Created successfully"),
    NO_CONTENT(HttpStatus.NO_CONTENT, 20400, "No content");

    private final HttpStatusCode httpStatusCode;
    private final Integer code;
    private final String description;
}
