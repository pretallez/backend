package com.pretallez.domain.chat.repository;

import java.util.List;
import java.util.Optional;

import com.pretallez.application.chat.dto.ParticipantResponse;
import com.pretallez.application.chat.dto.ParticipatedChatRoomResponse;
import com.pretallez.domain.chat.entity.Participation;

public interface ParticipationRepository {
	// Command
	Participation save(Participation participant);
	void delete(Participation participant);

	// Query
	Optional<Participation> findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);
	boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

	List<ParticipatedChatRoomResponse> findByMemberId(Long memberId);

	List<ParticipantResponse> findAllWithMemberByChatRoomId(Long chatRoomId);

}
