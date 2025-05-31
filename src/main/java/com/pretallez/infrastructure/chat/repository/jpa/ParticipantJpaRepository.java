package com.pretallez.infrastructure.chat.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pretallez.domain.chat.entity.Participation;

@Repository
public interface ParticipantJpaRepository extends JpaRepository<Participation, Long> {

	boolean existsByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

	Optional<Participation> findByMemberIdAndChatRoomId(Long chatRoomId, Long memberId);

	void deleteByMemberIdAndChatRoomId(Long memberId, Long chatRoomId);

}
