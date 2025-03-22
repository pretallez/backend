package com.pretallez.unit.service;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.ChatFixture;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatRead;
import com.pretallez.domain.chat.dto.PendingMessage;
import com.pretallez.domain.chat.repository.ChatRepository;
import com.pretallez.domain.chat.service.ChatBatchScheduler;
import com.pretallez.domain.chat.service.ChatConsumer;
import com.pretallez.domain.member.service.MemberService;
import com.rabbitmq.client.Channel;

@DisplayName("채팅 배치 스케줄러 단위 테스트")
public class ChatBatchSchedulerUnitTest {

	@InjectMocks
	private ChatBatchScheduler chatBatchScheduler;

	@Mock
	private ChatRepository chatRepository;

	@Mock
	private MemberService memberService;

	@Mock
	private ChatConsumer chatConsumer;

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private ZSetOperations<String, Object> zSetOperations;

	@Mock
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
	}

	@Test
	@DisplayName("채팅 배치 실행 시, 성공적으로 처리하고 Redis에 캐싱")
	void processBatch_Success() throws Exception {
		// Given
		ChatCreate.Request request = ChatFixture.chatCreateReqeust();
		PendingMessage pendingMessage = new PendingMessage(1L, mock(Channel.class), request);
		String jsonString = "{\"memberId\":1,\"chatroomId\":1,\"nickname\":\"testUser\",\"content\":\"Hello\"}";
		ChatRead.Response response = ChatFixture.chatReadResponse();

		when(chatConsumer.drainPendingMessages()).thenReturn(List.of(pendingMessage));
		when(chatRepository.saveAll(anyList())).thenReturn(List.of(ChatFixture.fakeChat(1L)));
		when(memberService.getNickname(1L)).thenReturn("testUser");
		when(objectMapper.writeValueAsString(any(ChatRead.Response.class))).thenReturn(jsonString); // ObjectMapper 모킹

		// When
		chatBatchScheduler.processBatch();

		// Then
		verify(chatConsumer).drainPendingMessages();
		verify(chatRepository).saveAll(anyList());
		verify(memberService).getNickname(1L);
		verify(objectMapper).writeValueAsString(any(ChatRead.Response.class));
		verify(redisTemplate.opsForZSet()).add(
			eq("chatroom:1"),
			eq(jsonString),
			anyDouble()
		);
	}
}
