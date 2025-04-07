package com.pretallez.common.response.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityErrorCode implements ResCode {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "회원(id:%d) 엔티티를 찾을 수 없습니다."),
	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "채팅방(id:%d) 엔티티를 찾을 수 없습니다."),
	MEMBER_CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "회원채팅방(회원id:%d, 채팅방id:%d) 엔티티를 찾을 수 없습니다."),
	BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "게시글(id:%d) 엔티티를 찾을 수 없습니다."),
	VOTEPOST_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "투표게시글(id:%d) 엔티티를 찾을 수 없습니다."),

	ENTITY_ERROR_MISMATCH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10000, "엔티티 조회 과정에서 에러가 발생했습니다. 관리자에게 문의해주세요.");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
