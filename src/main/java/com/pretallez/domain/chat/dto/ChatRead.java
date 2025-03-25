package com.pretallez.domain.chat.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pretallez.common.entity.Chat;
import com.pretallez.common.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRead {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private Long chatroomId;
		private Long memberId;
		private String nickname;
		private String content;
		private MessageType messageType;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
		private LocalDateTime createdAt;

		public static Response from(Chat chat, String nickname) {
			return new Response(
				chat.getId(),
				chat.getChatroomId(),
				chat.getMemberId(),
				nickname,
				chat.getContent(),
				chat.getMessageType(),
				chat.getCreatedAt()
			);
		}
	}


}
