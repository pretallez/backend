package com.pretallez.domain.chat.entity;

import java.time.LocalDateTime;

import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.vo.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long senderId;

	private Long chatRoomId;

	private String content;

	private MessageType messageType;

	private LocalDateTime createdAt;

	private ChatMessage(Long senderId, Long chatRoomId, String content, MessageType messageType) {
		this.senderId = senderId;
		this.chatRoomId = chatRoomId;
		this.content = content;
		this.messageType = messageType;
		this.createdAt = LocalDateTime.now();
	}

	public static ChatMessage createMessage(Long senderId, Long chatRoomId, String content, MessageType messageType) {
		if (content.length() > 500) {
			throw new ChatException(ChatErrorCode.MESSAGE_LENGTH_EXCEEDED);
		}
		return new ChatMessage(senderId, chatRoomId, content, messageType);
	}
}
