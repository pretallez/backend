package com.pretallez.infrastructure.chat.repository;

import static com.pretallez.domain.chat.entity.QChatRoom.*;
import static com.pretallez.domain.chat.entity.QParticipation.*;
import static com.pretallez.domain.member.entity.QMember.*;
import static com.pretallez.domain.posting.entity.QVotePost.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pretallez.application.chat.dto.ParticipantResponse;
import com.pretallez.application.chat.dto.ParticipatedChatRoomResponse;
import com.pretallez.domain.chat.entity.Participation;
import com.pretallez.domain.chat.repository.ParticipationRepository;
import com.pretallez.infrastructure.chat.repository.jpa.ParticipantJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParticipationRepositoryImpl implements ParticipationRepository {

	private final ParticipantJpaRepository participantJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public Participation save(Participation participant) {
		return participantJpaRepository.save(participant);
	}

	@Override
	public void delete(Participation participation) {
		participantJpaRepository.delete(participation);
	}

	@Override
	public Optional<Participation> findByMemberIdAndChatRoomId(Long memberId, Long chatRoomId) {
		return participantJpaRepository.findByMemberIdAndChatRoomId(memberId, chatRoomId);
	}

	@Override
	public boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId) {
		return participantJpaRepository.existsByMemberIdAndChatRoomId(memberId, chatRoomId);
	}

	@Override
	public List<ParticipatedChatRoomResponse> findByMemberId(Long memberId) {
		return queryFactory
			.select(Projections.constructor(
				ParticipatedChatRoomResponse.class,
				chatRoom.id,
				votePost.id,
				chatRoom.boardTitle
			))
			.from(participation)
			.join(participation.chatRoom, chatRoom)
			.join(chatRoom.votePost, votePost)
			.where(participation.member.id.eq(memberId))
			.fetch();
	}

	@Override
	public List<ParticipantResponse> findAllWithMemberByChatRoomId(Long chatRoomId) {
		return queryFactory
			.select(Projections.constructor(
				ParticipantResponse.class,
				member.id,
				member.nickname
			))
			.from(participation)
			.join(participation.member, member)
			.where(participation.chatRoom.id.eq(chatRoomId))
			.fetch();
	}
}
