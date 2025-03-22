package com.pretallez.domain.chat.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.pretallez.domain.chat.dto.ChatCreate;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
