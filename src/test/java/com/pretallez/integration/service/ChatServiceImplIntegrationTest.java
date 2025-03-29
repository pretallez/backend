package com.pretallez.integration.service;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.pretallez.common.entity.MemberChatroom;
import com.pretallez.common.fixture.ChatFixture;
import com.pretallez.common.fixture.TestFixtureFactory;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.repository.ChatRepository;
import com.pretallez.domain.chat.service.ChatService;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("채팅 서비스 통합 테스트")
class ChatServiceImplIntegrationTest {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private TestFixtureFactory testFixtureFactory;

	MemberChatroom savedMemberChatroom;

	@BeforeEach
	void setUp() {
		savedMemberChatroom = testFixtureFactory.createAndJoinChatroom();
	}

	@Test
	@DisplayName("채팅 저장 시, RabbitMQ를 통해 배치 처리 후 레디스에 캐싱")
	void addChat_Success() throws InterruptedException {
		// Given
		ChatCreate.Request request = ChatFixture.chatCreateReqeustWithMemberChatroom(savedMemberChatroom);

		// When
		chatService.addChat(request);
		TimeUnit.SECONDS.sleep(6);

		// Then
		Assertions.assertFalse(chatRepository.findAll().isEmpty());
	}
}