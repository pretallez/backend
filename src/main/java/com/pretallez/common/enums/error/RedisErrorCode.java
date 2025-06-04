package com.pretallez.common.enums.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisErrorCode implements ResCode {
	REDIS_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "레디스 저장에 실패했습니다."),
	REDIS_FIND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "레디스 조회에 실패했습니다."),
	REDIS_JSON_DESERIALIZATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "레디스 역직렬화에 실패했습니다.");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
