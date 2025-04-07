package com.pretallez.common.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisErrorCode implements ResCode {
	REDIS_CACHE_ADD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "채팅 메시지 Reids 캐싱에 실패했습니다. (채팅방id:%d, 회원id:%d, 채팅내용:%s)"),
	REDIS_HISTORY_TRIM_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Redis 히스토리 정리에 실패했습니다. :%s"),
	REDIS_JSON_SERIALIZATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Redis 데이터 JSON 직렬화에 실패했습니다."),
	REDIS_CACHE_GET_NICKNAME_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "Redis 캐시에서 회원 닉네임 조회에 실패했습니다. (회원id:%d)");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
