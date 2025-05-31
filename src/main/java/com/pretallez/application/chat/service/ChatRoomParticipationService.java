package com.pretallez.application.chat.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.application.chat.dto.ParticipantResponse;
import com.pretallez.application.chat.dto.ParticipatedChatRoomResponse;
import com.pretallez.application.chat.port.input.ChatRoomParticipationUseCase;
import com.pretallez.domain.chat.entity.ChatRoom;
import com.pretallez.domain.chat.entity.Participation;
import com.pretallez.domain.chat.event.ParticipantJoinedEvent;
import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.policy.ParticipationPolicy;
import com.pretallez.domain.chat.repository.ChatRoomRepository;
import com.pretallez.domain.chat.repository.ParticipationRepository;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomParticipationService implements ChatRoomParticipationUseCase {

	private final ParticipationPolicy participationPolicy;

	private final MemberRepository memberRepository;
	private final ParticipationRepository participationRepository;
	private final ChatRoomRepository chatRoomRepository;

	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public void join(Long memberId, Long chatRoomId) {
		participationPolicy.assertNotParticipating(memberId, chatRoomId);

		Member member = getMember(memberId);
		ChatRoom chatRoom = getChatRoom(chatRoomId);

		Participation participant = Participation.join(member, chatRoom);
		participationRepository.save(participant);

		eventPublisher.publishEvent(new ParticipantJoinedEvent(memberId, chatRoomId));
	}

	@Override
	@Transactional
	public void leave(Long memberId, Long chatRoomId) {
		participationRepository.delete(getParticipation(memberId, chatRoomId));
	}

	@Override
	public List<ParticipantResponse> getParticipants(Long chatRoomId) {
		return participationRepository.findAllWithMemberByChatRoomId(chatRoomId);
	}

	@Override
	public List<ParticipatedChatRoomResponse> getParticipatedChatRooms(Long memberId) {
		return participationRepository.findByMemberId(memberId);
	}

	private ChatRoom getChatRoom(Long chatRoomId) {
		return chatRoomRepository.findById(chatRoomId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.CHATROOM_NOT_FOUND, chatRoomId));
	}

	private Participation getParticipation(Long memberId, Long chatRoomId) {
		return participationRepository.findByMemberIdAndChatRoomId(memberId, chatRoomId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.NOT_PARTICIPATING, memberId, chatRoomId));
	}

	private Member getMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new ChatException(ChatErrorCode.MEMBER_NOT_FOUND, memberId));
	}
}
