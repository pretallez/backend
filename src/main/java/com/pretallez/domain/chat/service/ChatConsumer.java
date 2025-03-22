package com.pretallez.domain.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.PendingMessage;
import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatConsumer {

	private static final String QUEUE_NAME = "chat.queue";
	private final ConcurrentLinkedQueue<PendingMessage> batchQueue = new ConcurrentLinkedQueue<>();

	@RabbitListener(queues = QUEUE_NAME, ackMode = "MANUAL", concurrency = "1")
	public void consumeChatMessage(ChatCreate.Request chatCreateRequest, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		try {
			log.info("Received from Queue[{}] - Tag: {}, Content: {}", QUEUE_NAME, tag, chatCreateRequest.getContent());
			batchQueue.add(new PendingMessage(tag, channel, chatCreateRequest));
		} catch (Exception e) {
			log.error("Failed to process message - Tag: {}, Error: {}", tag, e.getMessage());
			throw new RuntimeException("Failed to receive message", e);
		}
	}

	public List<PendingMessage> drainPendingMessages() {
		List<PendingMessage> messages = new ArrayList<>();
		while (!batchQueue.isEmpty()) {
			PendingMessage message = batchQueue.poll();
			if (message != null) {
				messages.add(message);
			}
		}
		return messages;
	}
}