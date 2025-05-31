package com.pretallez.domain.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.pretallez.common.response.ResCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ResCode {
	NOT_PARTICIPATING(HttpStatus.BAD_REQUEST, 40000, "채팅방에 참가 중이지 않습니다. (memberId: %d, chatRoomId: %d)"),
	VOTE_POST_REQUIRED(HttpStatus.BAD_REQUEST, 40001, "채팅방 생성을 위해 투표글은 필수입니다."),
	MESSAGE_LENGTH_EXCEEDED(HttpStatus.BAD_REQUEST, 40002, "메시지는 500자 이하로 제한됩니다."),

	ALREADY_PARTICIPATING(HttpStatus.CONFLICT, 40900, "이미 채팅방에 참가 중입니다. (memberId: %d, chatRoomId: %d)"),

	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "존재하지 않는 채팅방입니다. (chatRoomId: %d)"),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "존재하지 않는 사용자입니다. (memberId: %d)"),
	VOTEPOST_NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "투표글이 존재하지 않습니다. (votePostId: %d)");

	private final HttpStatusCode httpStatusCode;
	private final Integer code;
	private final String description;
}
