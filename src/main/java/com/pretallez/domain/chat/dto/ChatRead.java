package com.pretallez.domain.chat.dto;

import java.time.LocalDateTime;

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
		private Long chatroomId;
		private Long memberId;
		private String nickname;
		private String content;
		private MessageType messageType;
		private LocalDateTime createdAt;
		private Long id;

		public static Response from(Chat chat, String nickname) {
			return new Response(
				chat.getChatroomId(),
				chat.getMemberId(),
				nickname,
				chat.getContent(),
				chat.getMessageType(),
				chat.getCreatedAt(),
				chat.getId()
			);
		}
	}


}
