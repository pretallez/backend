package com.pretallez.application.chat.port.output;

import com.pretallez.application.chat.dto.ChatMessageResponse;

public interface ChatRoomBroadcaster {
	void broadcast(ChatMessageResponse response);
}
