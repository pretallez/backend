package com.pretallez.unit.chat.domain.policy;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.policy.ParticipationPolicy;
import com.pretallez.domain.chat.repository.ParticipationRepository;

class ParticipationPolicyTest {

	private ParticipationRepository mockRepository;
	private ParticipationPolicy sut;

	@BeforeEach
	void setUp() {
		mockRepository = mock(ParticipationRepository.class);
		sut = new ParticipationPolicy(mockRepository);
	}

	@Test
	void 채팅_메시지_전송_시_채팅방에_참여중이면_예외를_던지지_않는다() {
		// given
		when(mockRepository.existsByMemberIdAndChatRoomId(1L, 1L))
			.thenReturn(true);

		// when
		// then
		assertThatCode(() -> sut.assertParticipating(1L, 1L))
			.doesNotThrowAnyException();
	}

	@Test
	void 채팅_메시지_전송_시_채팅방에_참여중이_아니면_예외를_던진다() {
		// given
		when(mockRepository.existsByMemberIdAndChatRoomId(1L, 1L))
			.thenReturn(false);

		// when
		ChatException ex = assertThrows(ChatException.class, () ->
			sut.assertParticipating(1L, 1L)
		);

		// then
		assertThat(ex.getResCode()).isEqualTo(ChatErrorCode.NOT_PARTICIPATING);
	}


	@Test
	void 채팅방_참가_시_아직_참여중이_아니면_예외를_던지지_않는다() {
		// given
		when(mockRepository.existsByMemberIdAndChatRoomId(1L, 1L))
			.thenReturn(false);

		// when
		// then
		assertThatCode(() -> sut.assertNotParticipating(1L, 1L))
			.doesNotThrowAnyException();
	}

	@Test
	void 채팅방_참가_시_이미_참여중이면_예외를_던진다() {
		// given
		when(mockRepository.existsByMemberIdAndChatRoomId(1L, 1L))
			.thenReturn(true);

		// when
		ChatException ex = assertThrows(ChatException.class, () ->
			sut.assertNotParticipating(1L, 1L)
		);

		// then
		assertThat(ex.getResCode()).isEqualTo(ChatErrorCode.ALREADY_PARTICIPATING);
	}
}