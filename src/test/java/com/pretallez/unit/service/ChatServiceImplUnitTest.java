package com.pretallez.unit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pretallez.common.fixture.ChatFixture;
import com.pretallez.domain.chatting.dto.chat.ChatCreate;
import com.pretallez.domain.chatting.service.chat.ChatProducer;
import com.pretallez.domain.chatting.service.chat.ChatServiceImpl;
import com.pretallez.domain.member.service.MemberService;
import com.pretallez.domain.chatting.service.memberchatroom.MemberChatroomService;

@DisplayName("채팅 서비스 단위 테스트")
class ChatServiceImplUnitTest {

	@InjectMocks
	private ChatServiceImpl chatService;

	@Mock
	private MemberChatroomService memberChatroomService;

	@Mock
	private ChatProducer chatProducer;

	@Mock
	private MemberService memberService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("채팅 메시지 추가 시, 메시지를 정상적으로 발행")
	void addChat_Success() {
		// Given
		ChatCreate.Request request = ChatFixture.chatCreateReqeust();
		when(memberService.getNickname(1L)).thenReturn("testUser");

		// When
		ChatCreate.Response response = chatService.addChat(request);

		// Then
		verify(memberChatroomService).checkMemberInChatroom(1L, 1L);
		verify(chatProducer).produceChatMessage(request);
		assertThat(response.getNickname()).isEqualTo("testUser");
		assertThat(response.getContent()).isEqualTo(request.getContent());

	}
}