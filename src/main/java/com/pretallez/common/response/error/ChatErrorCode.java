package com.pretallez.common.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ResCode {
	CHAT_PROCESS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "채팅 메시지 처리에 실패했습니다. (채팅방id:%d, 회원id:%d, 채팅내용:%s, 태그:%d)"),

	CHAT_ERROR_MISMATCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "채팅 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
