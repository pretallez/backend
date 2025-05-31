package com.pretallez.application.chat.port.input;

import java.util.List;

import com.pretallez.application.chat.dto.ParticipantResponse;
import com.pretallez.application.chat.dto.ParticipatedChatRoomResponse;

public interface ChatRoomParticipationUseCase {
	// Command
	void join(Long memberId, Long chatRoomId);
	void leave(Long memberId, Long chatRoomId);

	// Query
	List<ParticipantResponse> getParticipants(Long chatRoomId);
	List<ParticipatedChatRoomResponse> getParticipatedChatRooms(Long memberId);
}
