package com.pretallez.domain.chatting.service.chat;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.pretallez.domain.chatting.dto.chat.ChatCreate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ChatProducer")
@RequiredArgsConstructor
@Service
public class ChatProducer {

	private static final String CHAT_EXCHAGE = "chat.exchange";
	private static final String CHAT_ROUTING_KEY = "chat.message";

	private final RabbitTemplate rabbitTemplate;

	public void produceChatMessage(ChatCreate.Request chatCreateRequest) {
		log.info("Producer >>>> Queue[chat.queue]: {}", chatCreateRequest);
		rabbitTemplate.convertAndSend(CHAT_EXCHAGE, CHAT_ROUTING_KEY, chatCreateRequest);
	}
}
