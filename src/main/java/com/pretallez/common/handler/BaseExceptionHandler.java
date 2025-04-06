package com.pretallez.common.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pretallez.common.exception.BaseException;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "BaseExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class BaseExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<CustomApiResponse<Void>> handleBaseExceptionHandler(BaseException baseException) {
		log.error("BaseException exception occurred: {}", baseException.getMessage(), baseException);

		ResCode errorCode = baseException.getResCode();

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(CustomApiResponse.ERROR(errorCode, baseException.getDescription()));
	}
}
