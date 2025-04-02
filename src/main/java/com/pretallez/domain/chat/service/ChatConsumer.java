package com.pretallez.domain.chat.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.entity.Chat;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatRead;
import com.pretallez.domain.chat.repository.ChatRepository;
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

	private final ChatRepository chatRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	private final MemberService memberService;
	private final ObjectMapper objectMapper;

	@RabbitListener(queues = QUEUE_NAME, ackMode = "MANUAL", concurrency = "10")
	@Transactional
	public void consumeChatMessage(ChatCreate.Request chatCreateRequest, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		try {
			// log.info("{} 메시지 소비 - 태그: {}, 내용: {}", QUEUE_NAME, tag, chatCreateRequest.getContent());

			// MariaDB에 즉시 저장
			Chat savedChat = chatRepository.save(ChatCreate.Request.toEntity(chatCreateRequest));

			// Redis 캐싱
			ChatRead.Response response = updateRedisWithChats(savedChat);

			// STOMP 브로드캐스트
			String destination = "/topic/v1.api.chatrooms." + savedChat.getChatroomId();
			messagingTemplate.convertAndSend(destination, response);

			// 메시지 ACK 처리
			channel.basicAck(tag, false);
			// log.info("메시지 ACK - 태그: {}", tag);
		} catch (Exception e) {
			log.error("{} 메시지 소비 실패 - 태그: {}, 에러: {}", QUEUE_NAME, tag, e.getMessage());
			try {
				channel.basicNack(tag, false, true);
			} catch (Exception nackEx) {
				log.error("{} 메시지 NACK 실패 - 태그: {}, 에러: {}", QUEUE_NAME, tag, e.getMessage());
			}
			// throw new RuntimeException("메시지 소비 처리 실패", e);
		}
	}

	public ChatRead.Response updateRedisWithChats(Chat chat) {
		// 회원 닉네임 조회
		String nickname = memberService.getNickname(chat.getMemberId());
		ChatRead.Response chatReadResponse = ChatRead.Response.from(chat, nickname);

		String chatroomKey = "chatroom:" + chat.getChatroomId();

		// Redis 캐싱
		redisTemplate.opsForZSet().add(
			chatroomKey,
			toJson(chatReadResponse),
			chat.getId()
		);

		// MAX_CHAT_HISTORY 만큼 유지
		trimRedisHistory(chatroomKey);

		return chatReadResponse;
	}

	private void trimRedisHistory(String chatroomKey) {
		Long size = redisTemplate.opsForZSet().size(chatroomKey);

		if (size != null && size > MAX_CHAT_HISTORY) {
			redisTemplate.opsForZSet().removeRange(chatroomKey, 0, size - MAX_CHAT_HISTORY - 1);
		}
	}

	private String toJson(ChatRead.Response chatReadResponse) {
		try {
			return objectMapper.writeValueAsString(chatReadResponse);
		} catch (Exception e) {
			throw new RuntimeException("채팅 JSON 직렬화에 실패했습니다.", e); // 커스텀 예외 처리 필요
		}
	}
}