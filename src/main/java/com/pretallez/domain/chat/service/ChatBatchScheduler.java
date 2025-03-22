package com.pretallez.domain.chat.service;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.entity.Chat;
import com.pretallez.common.util.ScoreUtil;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatRead;
import com.pretallez.domain.chat.dto.PendingMessage;
import com.pretallez.domain.chat.repository.ChatRepository;
import com.pretallez.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class ChatBatchScheduler {

	private static final int BATCH_PROCESS_INTERVAL_MS = 5000;
	private static final int MAX_CHAT_HISTORY = 300;

	private final ChatRepository chatRepository;
	private final MemberService memberService;
	private final ChatConsumer chatConsumer;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper objectMapper;

	@Scheduled(fixedRate = BATCH_PROCESS_INTERVAL_MS)
	public void processBatch() {
		List<PendingMessage> messagesToProcess = chatConsumer.drainPendingMessages();
		if (messagesToProcess.isEmpty()) {
			return;
		}
		log.info("[배치 처리 시작] - 처리할 메시지 수: {}", messagesToProcess.size());

		try {
			List<Chat> savedChats = saveMessages(messagesToProcess);
			updateRedisWithChats(savedChats);

			acknowledgeMessages(messagesToProcess);
			log.info("[배치 처리 완료] - 처리된 메시지 수: {}", messagesToProcess.size());
		} catch (Exception e) {
			log.error("[배치 처리 실패] - 오류: {}", e.getMessage());
			throw new RuntimeException("채팅 배치 처리에 실패했습니다.", e); // 커스텀 예외 처리 필요
		}
	}

	@Transactional
	public List<Chat> saveMessages(List<PendingMessage> messages) {
		List<Chat> chatEntities = messages.stream()
			.map(PendingMessage::getChatCreateRequest)
			.map(ChatCreate.Request::toEntity)
			.toList();
		return chatRepository.saveAll(chatEntities);
	}

	public void updateRedisWithChats(List<Chat> chats) {
		chats.forEach(chat -> {
			String chatroomKey = buildRedisChatroomKey(chat.getChatroomId());
			String nickname = memberService.getNickname(chat.getMemberId());
			ChatRead.Response chatReadResponse = ChatRead.Response.from(chat, nickname);
			String messageJson = toJson(chatReadResponse);

			double score = ScoreUtil.buildScore(chat.getCreatedAt(), chat.getId());
			redisTemplate.opsForZSet().add(chatroomKey, messageJson, score);
			trimRedisHistory(chatroomKey);
		});
	}

	private void trimRedisHistory(String chatroomKey) {
		Long size = redisTemplate.opsForZSet().size(chatroomKey);
		if (size != null && size > MAX_CHAT_HISTORY) {
			redisTemplate.opsForZSet().removeRange(chatroomKey, 0, size - MAX_CHAT_HISTORY - 1);
		}
	}

	private void acknowledgeMessages(List<PendingMessage> messages) {
		messages.forEach(this::acknowledgeMessage);
	}

	private void acknowledgeMessage(PendingMessage message) {
		try {
			message.getChannel().basicAck(message.getTag(), false);
			log.info("메시지 ACK 처리 - 태그: {}, 내용: {}", message.getTag(), message.getChatCreateRequest().getContent());
		} catch (Exception e) {
			handleAckFailure(message, e);
		}
	}

	private void handleAckFailure(PendingMessage message, Exception e) {
		try {
			message.getChannel().basicNack(message.getTag(), false, true); // 재큐잉 옵션 추가
			log.warn("메시지 NACK 처리 - 태그: {}, 오류: {}", message.getTag(), e.getMessage());
		} catch (Exception nackException) {
			log.error("메시지 NACK 실패 - 태그: {}, 오류: {}", message.getTag(), nackException.getMessage());
		}
	}

	private String buildRedisChatroomKey(Long chatroomId) {
		return "chatroom:" + chatroomId;
	}

	private String toJson(ChatRead.Response chatReadResponse) {
		try {
			return objectMapper.writeValueAsString(chatReadResponse);
		} catch (Exception e) {
			throw new RuntimeException("채팅 JSON 직렬화에 실패했습니다.", e); // 커스텀 예외 처리 필요
		}
	}
}
