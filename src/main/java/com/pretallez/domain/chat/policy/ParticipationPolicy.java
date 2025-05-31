package com.pretallez.domain.chat.policy;

import org.springframework.stereotype.Component;

import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.repository.ParticipationRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParticipationPolicy {

	private final ParticipationRepository participantRepository;

	public void assertParticipating(Long memberId, Long chatRoomId) {
		if (!participantRepository.existsByMemberIdAndChatRoomId(memberId, chatRoomId)) {
			throw new ChatException(ChatErrorCode.NOT_PARTICIPATING, memberId, chatRoomId);
		}
	}

	public void assertNotParticipating(Long memberId, Long chatRoomId) {
		if (participantRepository.existsByMemberIdAndChatRoomId(memberId, chatRoomId)) {
			throw new ChatException(ChatErrorCode.ALREADY_PARTICIPATING, memberId, chatRoomId);
		}
	}
}
