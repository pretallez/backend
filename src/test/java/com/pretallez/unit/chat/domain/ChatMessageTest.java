package com.pretallez.unit.chat.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.pretallez.domain.chat.entity.ChatMessage;
import com.pretallez.domain.chat.exception.ChatErrorCode;
import com.pretallez.domain.chat.exception.ChatException;
import com.pretallez.domain.chat.vo.MessageType;

class ChatMessageTest {

	private final Long memberId = 1L;
	private final Long chatRoomId = 1L;

	@Test
	void 메시지가_500자_이하이면_정상적으로_생성된다() {
		// given
		String message = "안녕하세요";

		// when
		ChatMessage sut = ChatMessage.createMessage(memberId, chatRoomId, message, MessageType.CHAT);

		// then
		assertThat(sut.getContent()).isEqualTo(message);
		assertThat(sut.getMessageType()).isEqualTo(MessageType.CHAT);
		assertThat(sut.getSenderId()).isEqualTo(memberId);
		assertThat(sut.getChatRoomId()).isEqualTo(chatRoomId);
	}

	@Test
	void 메시지가_500자를_초과하면_예외를_던진다() {
		// given
		String message = "a".repeat(501);

		// when
		ChatException ex = assertThrows(ChatException.class, () ->
			ChatMessage.createMessage(memberId, chatRoomId, message, MessageType.CHAT)
		);

		// then
		assertThat(ex.getResCode()).isEqualTo(ChatErrorCode.MESSAGE_LENGTH_EXCEEDED);
	}
}