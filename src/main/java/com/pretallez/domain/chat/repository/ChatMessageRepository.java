package com.pretallez.domain.chat.repository;

import java.util.List;

import com.pretallez.domain.chat.entity.ChatMessage;

public interface ChatMessageRepository {
	// Command
	ChatMessage save(ChatMessage chatMessage);

	// Query
	List<ChatMessage> findRecentMessages(Long chatRoomId, Long lastMessageId, int size);
}
