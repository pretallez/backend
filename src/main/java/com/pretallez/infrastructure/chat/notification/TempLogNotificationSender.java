package com.pretallez.infrastructure.chat.notification;

import org.springframework.stereotype.Component;

import com.pretallez.application.chat.port.output.ChatRoomNotificationSender;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TempLogNotificationSender implements ChatRoomNotificationSender {

	@Override
	public void notifyParticipantJoined(Long memberId, Long chatRoomId) {
		log.info("notification");
	}
}
