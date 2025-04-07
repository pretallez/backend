package com.pretallez.common.fixture;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.test.util.ReflectionTestUtils;

import com.pretallez.common.entity.Chat;
import com.pretallez.common.entity.MemberChatroom;
import com.pretallez.common.enums.MessageType;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatQueryRequest;
import com.pretallez.domain.chat.dto.ChatRead;

public class ChatFixture {

	public static Chat fakeChat(Long id) {
		Long memberId = 1L;
		Long chatroomId = 1L;
		String content = "Hello !!";
		MessageType messageType = MessageType.CHAT;
		LocalDateTime createdAt = LocalDateTime.now();
		Chat chat = Chat.of(memberId, chatroomId, content, messageType, createdAt);
		ReflectionTestUtils.setField(chat, "id", id);

		return chat;
	}

	public static ChatCreate.Request chatCreateReqeust() {
		return new ChatCreate.Request(1L, 1L, "Hello", MessageType.CHAT);
	}

	public static ChatCreate.Response chatCreateResponse() {
		return new ChatCreate.Response(1L, 1L, "testUser", "Hello", MessageType.CHAT, LocalDateTime.now());
	}

	public static ChatCreate.Request chatCreateReqeustWithMemberChatroom(MemberChatroom memberChatroom) {
		return new ChatCreate.Request(memberChatroom.getMember().getId(), memberChatroom.getChatroom().getId(), "Hello", MessageType.CHAT);
	}

	public static ChatRead.Response chatReadResponse() {
		return new ChatRead.Response(1L, 1L, 1L, "testUser", "Hello", MessageType.CHAT, LocalDateTime.now());
	}

	public static ChatQueryRequest chatQueryRequest() {
		return new ChatQueryRequest(1L, 4L, 20);
	}

	public static List<ChatRead.Response> chatReadResponses() {
		return List.of(
			new ChatRead.Response(3L, 1L, 1L, "임종엽", "채팅내용3", MessageType.CHAT, LocalDateTime.of(2025, 3, 24, 11, 7)),
			new ChatRead.Response(2L, 1L, 2L, "김성호", "채팅내용2", MessageType.CHAT, LocalDateTime.of(2025, 3, 24, 11, 6)),
			new ChatRead.Response(1L, 1L, 1L, "임종엽", "채팅내용1", MessageType.CHAT, LocalDateTime.of(2025, 3, 24, 11, 5))
		);
	}
}
