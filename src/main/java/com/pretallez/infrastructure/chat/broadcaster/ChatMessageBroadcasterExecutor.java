package com.pretallez.infrastructure.chat.broadcaster;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.output.ChatRoomBroadcaster;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageBroadcasterExecutor {

	private final ChatRoomBroadcaster broadcaster;

	@Async
	public void broadcast(ChatMessageResponse response) {
		try {
			broadcaster.broadcast(response);
		} catch (Exception e) {
			log.error("Failed to broadcast chat message: {}", response, e);
		}
	}
}
