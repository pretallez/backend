package com.pretallez.application.chat.port.input;

import java.util.List;

import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.dto.ChatMessageRequest;

public interface ChatMessageUseCase {
	// Command
	void sendMessage(ChatMessageRequest request);

	// Query
	List<ChatMessageResponse> getRecentMessages(Long chatRoomId, Long lastMessageId, int size);
}
