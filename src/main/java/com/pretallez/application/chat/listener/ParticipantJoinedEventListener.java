package com.pretallez.application.chat.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pretallez.application.chat.port.output.ChatRoomNotificationSender;
import com.pretallez.domain.chat.event.ParticipantJoinedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParticipantJoinedEventListener {

	private final ChatRoomNotificationSender notificationSender;

	@EventListener
	public void handle(ParticipantJoinedEvent event) {
		log.info("ParticipantJoinedEvent");

		// Todo: 채팅방 참가 성공 알림 발송 (e.g. 카카오 알림 또는 웹 서비스 알림): 현재 미구현
		notificationSender.notifyParticipantJoined(event.memberId(), event.chatRoomId());
	}
}
