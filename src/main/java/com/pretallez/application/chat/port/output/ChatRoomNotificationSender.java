package com.pretallez.application.chat.port.output;

public interface ChatRoomNotificationSender {
	void notifyParticipantJoined(Long memberId, Long chatRoomId);
}
