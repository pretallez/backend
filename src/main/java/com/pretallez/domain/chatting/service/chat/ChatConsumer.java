package com.pretallez.domain.chatting.service.chat;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.domain.chatting.entity.Chat;
import com.pretallez.domain.chatting.exception.ChatProcessException;
import com.pretallez.common.exception.RedisException;
import com.pretallez.domain.chatting.enums.ChatErrorCode;
import com.pretallez.common.enums.error.RabbitMqErrorCode;
import com.pretallez.common.enums.error.RedisErrorCode;
import com.pretallez.domain.chatting.dto.chat.ChatCreate;
import com.pretallez.domain.chatting.dto.chat.ChatRead;
import com.pretallez.domain.chatting.repository.chat.ChatRepository;
import com.pretallez.domain.member.service.MemberService;
import com.rabbitmq.client.Channel;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatConsumer {
	private static final String QUEUE_NAME = "chat.queue";
	private static final int MAX_CHAT_HISTORY = 300;
	private static final String CHATROOM_KEY_PREFIX = "chatroom:";
	private static final String CHAT_BROADCAST_PREFIX = "/topic/v1.api.chatrooms.";

	private final ChatRepository chatRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	private final MemberService memberService;
	private final ObjectMapper objectMapper;

	@RabbitListener(queues = QUEUE_NAME, ackMode = "MANUAL", concurrency = "1")
	@Transactional
	public void consumeChatMessage(ChatCreate.Request chatCreateRequest, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		try {
			Chat savedChat = chatRepository.save(ChatCreate.Request.toEntity(chatCreateRequest));
			ChatRead.Response response = updateRedisWithChats(savedChat);

			broadcastToChatroom(savedChat.getChatroomId(), response);
			handleAck(channel, tag);
		} catch (Exception e) {
			throw new ChatProcessException(
				ChatErrorCode.CHAT_PROCESS_FAILED, e, chatCreateRequest.getMemberId(), chatCreateRequest.getChatroomId(), chatCreateRequest.getContent(), tag);
		}
	}

	private void handleAck(Channel channel, long tag) {
		try {
			channel.basicAck(tag, false);
			log.info("메시지 ACK 처리 성공 - 태그: {}", tag);
		} catch (Exception e) {
			throw new ChatProcessException(RabbitMqErrorCode.MESSEAGE_ACK_FAILED, QUEUE_NAME, e, tag);
		}

	}

	private void handleNack(Channel channel, long tag) {
		try {
			channel.basicNack(tag, false, false);
			log.info("메시지 NACK 처리 성공 - 태그: {}", tag);
		} catch (Exception e) {
			throw new ChatProcessException(RabbitMqErrorCode.MESSEAGE_NACK_FAILED, QUEUE_NAME, e, tag);
		}
	}

	private ChatRead.Response updateRedisWithChats(Chat chat) {
		try {
			String nickname = memberService.getNickname(chat.getMemberId());
			ChatRead.Response chatReadResponse = ChatRead.Response.from(chat, nickname);

			String chatroomKey = CHATROOM_KEY_PREFIX + chat.getChatroomId();

			redisTemplate.opsForZSet().add(chatroomKey, toJson(chatReadResponse), chat.getId());
			trimRedisHistory(chatroomKey);

			return chatReadResponse;
		} catch (Exception e) {
			throw new RedisException(RedisErrorCode.REDIS_CACHE_ADD_FAILED, e, chat.getChatroomId(), chat.getMemberId(), chat.getContent());
		}
	}

	private void trimRedisHistory(String chatroomKey) {
		try {
			Long size = redisTemplate.opsForZSet().size(chatroomKey);
			if (size != null && size > MAX_CHAT_HISTORY) {
				redisTemplate.opsForZSet().removeRange(chatroomKey, 0, size - MAX_CHAT_HISTORY - 1);
			}
		} catch (Exception e) {
			throw new RedisException(RedisErrorCode.REDIS_HISTORY_TRIM_FAILED, e, chatroomKey);
		}
	}

	private void broadcastToChatroom(Long chatroomId, ChatRead.Response response) {
		String destination = CHAT_BROADCAST_PREFIX + chatroomId;
		messagingTemplate.convertAndSend(destination, response);
	}

	private String toJson(ChatRead.Response chatReadResponse) {
		try {
			return objectMapper.writeValueAsString(chatReadResponse);
		} catch (Exception e) {
			throw new RedisException(RedisErrorCode.REDIS_JSON_SERIALIZATION_FAILED, e);
		}
	}
}