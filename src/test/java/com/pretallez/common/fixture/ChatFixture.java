package com.pretallez.common.fixture;

import java.time.LocalDateTime;

import org.springframework.test.util.ReflectionTestUtils;

import com.pretallez.common.entity.Chat;
import com.pretallez.common.entity.MemberChatroom;
import com.pretallez.common.enums.MessageType;
import com.pretallez.domain.chat.dto.ChatCreate;
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

	public static ChatCreate.Request chatCreateReqeustWithMemberChatroom(MemberChatroom memberChatroom) {
		return new ChatCreate.Request(memberChatroom.getMember().getId(), memberChatroom.getChatroom().getId(), "Hello", MessageType.CHAT);
	}

	public static ChatRead.Response chatReadResponse() {
		return new ChatRead.Response(1L, 1L, 1L, "testUser", "Hello", MessageType.CHAT, LocalDateTime.now());
	}
}
