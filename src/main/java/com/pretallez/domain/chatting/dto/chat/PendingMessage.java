package com.pretallez.domain.chatting.dto.chat;

import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PendingMessage {

	private final long tag;
	private final Channel channel;
	private final ChatCreate.Request chatCreateRequest;
}
