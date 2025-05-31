package com.pretallez.unit.chat.application;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pretallez.application.chat.dto.ChatMessageRequest;
import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.output.ChatNicknameProvider;
import com.pretallez.application.chat.service.ChatMessageService;
import com.pretallez.domain.chat.entity.ChatMessage;
import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.policy.ParticipationPolicy;
import com.pretallez.domain.chat.repository.ChatMessageRepository;
import com.pretallez.domain.chat.vo.MessageType;
import com.pretallez.infrastructure.chat.broadcaster.ChatMessageBroadcasterExecutor;

class ChatMessageServiceTest {

	private ParticipationPolicy participationPolicy;
	private ChatMessageBroadcasterExecutor broadCaster;
	private ChatMessageRepository messageRepository;
	private ChatNicknameProvider nicknameProvider;

	private ChatMessageService sut;

	@BeforeEach
	void setUp() {
		participationPolicy = mock(ParticipationPolicy.class);
		broadCaster = mock(ChatMessageBroadcasterExecutor.class);
		messageRepository = mock(ChatMessageRepository.class);
		nicknameProvider = mock(ChatNicknameProvider.class);

		sut = new ChatMessageService(
			participationPolicy, messageRepository, nicknameProvider, broadCaster
		);
	}

	@Test
	void 메시지를_보내면_닉네임을_조회하고_메시지를_저장한_후_브로드캐스트한다() {
		// given
		ChatMessageRequest request = new ChatMessageRequest(1L, 1L, "안녕", MessageType.CHAT);

		when(messageRepository.save(any()))
			.thenReturn(ChatMessage.createMessage(1L,1L,"안녕",MessageType.CHAT));

		when(nicknameProvider.getNicknames(Set.of(1L)))
			.thenReturn(Map.of(1L,"임종엽"));

		// when
		sut.sendMessage(request);

		// then
		verify(participationPolicy).assertParticipating(1L, 1L);
		verify(messageRepository).save(any(ChatMessage.class));
		verify(broadCaster).broadcast(any(ChatMessageResponse.class));
	}

	@Test
	void 참여하지_않은_사용자가_메시지를_보내면_예외를_던지고_실패한다() {
		// given
		ChatMessageRequest request = new ChatMessageRequest(1L, 1L, "안녕", MessageType.CHAT);

		doThrow(new ChatException(ChatErrorCode.NOT_PARTICIPATING, 1L, 1L))
			.when(participationPolicy)
			.assertParticipating(1L, 1L);

		// when
		ChatException ex = assertThrows(ChatException.class, () -> sut.sendMessage(request));

		// then
		assertThat(ex.getResCode()).isEqualTo(ChatErrorCode.NOT_PARTICIPATING);
		verify(messageRepository, never()).save(any());
		verify(broadCaster, never()).broadcast(any());
	}

	@Test
	void 마지막_메시지ID가_없으면_최신_메시지를_조회하고_닉네임을_조합한다() {
		// given
		when(messageRepository.fetchRecentMessages(1L, null, 2))
			.thenReturn(List.of(
				ChatMessage.createMessage(1L, 1L, "채팅1", MessageType.CHAT),
				ChatMessage.createMessage(2L, 1L, "채팅2", MessageType.CHAT)
			));

		when(nicknameProvider.getNicknames(Set.of(1L, 2L)))
			.thenReturn(Map.of(
				1L, "임종엽",
				2L, "김성호"
			));

		// when
		List<ChatMessageResponse> responses = sut.getRecentMessages(1L, null, 2);

		// then
		assertThat(responses.get(0).senderNickname()).isEqualTo("임종엽");
		assertThat(responses.get(0).content()).isEqualTo("채팅1");
		assertThat(responses.get(1).senderNickname()).isEqualTo("김성호");
		assertThat(responses.get(1).content()).isEqualTo("채팅2");

		verify(messageRepository).fetchRecentMessages(1L, null, 2);
		verify(nicknameProvider).getNicknames(Set.of(1L, 2L));
	}

	@Test
	void 마지막_메시지ID_기준으로_이전_메시지를_내림차순_조회하고_닉네임을_조합한다() {
		// given
		when(messageRepository.fetchRecentMessages(1L, 3L, 2))
			.thenReturn(List.of(
				ChatMessage.createMessage(1L, 1L, "채팅1", MessageType.CHAT),
				ChatMessage.createMessage(2L, 1L, "채팅2", MessageType.CHAT)
			));

		when(nicknameProvider.getNicknames(Set.of(1L, 2L)))
			.thenReturn(Map.of(
				1L, "임종엽",
				2L, "김성호"
			));

		// when
		List<ChatMessageResponse> responses = sut.getRecentMessages(1L, 3L, 2);

		// then
		assertThat(responses.get(0).senderNickname()).isEqualTo("임종엽");
		assertThat(responses.get(0).content()).isEqualTo("채팅1");
		assertThat(responses.get(1).senderNickname()).isEqualTo("김성호");
		assertThat(responses.get(1).content()).isEqualTo("채팅2");

		verify(messageRepository).fetchRecentMessages(1L, 3L, 2);
		verify(nicknameProvider).getNicknames(Set.of(1L, 2L));
	}
}