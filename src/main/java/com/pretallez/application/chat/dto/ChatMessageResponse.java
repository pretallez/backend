package com.pretallez.application.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageResponse(
	Long id,
	Long chatRoomId,
	Long senderId,
	String senderNickname,
	String content,
	LocalDateTime createdAt
) { }
