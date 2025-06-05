package com.pretallez.domain.chat.event;

public record ParticipantJoinedEvent(Long memberId, Long chatRoomId) { }
