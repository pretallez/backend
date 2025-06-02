package com.pretallez.application.chat.dto;

import com.pretallez.domain.chat.vo.MessageType;

public record ChatMessageRequest(
	Long chatRoomId,
	Long senderId,
	String content,
	MessageType messageType
) { }
