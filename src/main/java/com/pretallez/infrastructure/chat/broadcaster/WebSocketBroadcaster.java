package com.pretallez.infrastructure.chat.broadcaster;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.output.ChatRoomBroadcaster;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketBroadcaster implements ChatRoomBroadcaster {

	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public void broadcast(ChatMessageResponse response) {
		messagingTemplate.convertAndSend(
			"/sub/chatrooms/" + response.chatRoomId(),
			response
		);
	}


}
