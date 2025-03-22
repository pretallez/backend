package com.pretallez.domain.chat.dto;

import java.time.LocalDateTime;

import com.pretallez.common.entity.Chat;
import com.pretallez.common.enums.MessageType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ChatCreate {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class Request {

		@NotNull(message = "memberId는 필수 입력 값입니다.")
		private Long memberId;

		@NotNull(message = "chatroomId는 필수 입력 값입니다.")
		private Long chatroomId;

		@NotNull(message = "content는 필수 입력 값입니다.")
		private String content;

		@NotNull(message = "messageType은 필수 입력 값입니다.")
		private MessageType messageType;

		private LocalDateTime createdAt = LocalDateTime.now();

		public Request(Long memberId, Long chatroomId, String content, MessageType messageType) {
			this.memberId = memberId;
			this.chatroomId = chatroomId;
			this.content = content;
			this.messageType = messageType;
			this.createdAt = LocalDateTime.now();
		}

		public static Chat toEntity(Request request) {
			return Chat.of(request.getMemberId(), request.getChatroomId(), request.getContent(), request.getMessageType(), request.getCreatedAt());
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private Long memberId;
		private Long chatroomId;
		private String nickname;
		private String content;
		private MessageType messageType;
		private LocalDateTime createdAt;
	}
}
