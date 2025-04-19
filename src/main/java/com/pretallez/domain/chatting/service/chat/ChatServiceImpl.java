package com.pretallez.domain.chatting.service.chat;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.domain.chatting.entity.Chat;
import com.pretallez.domain.chatting.dto.chat.ChatCreate;
import com.pretallez.domain.chatting.dto.chat.ChatQueryRequest;
import com.pretallez.domain.chatting.dto.chat.ChatRead;
import com.pretallez.domain.chatting.repository.chat.ChatRepository;
import com.pretallez.domain.member.service.MemberService;
import com.pretallez.domain.chatting.service.memberchatroom.MemberChatroomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRepository chatRepository;
	private final ChatProducer chatProducer;
	private final RedisTemplate<String, String> redisTemplate;

	private final MemberChatroomService memberChatroomService;
	private final MemberService memberService;

	private final ObjectMapper objectMapper;


	@Override
	public ChatCreate.Response addChat(ChatCreate.Request chatCreateRequest) {
		// 회원이 채팅방에 속해 있는지 확인
		memberChatroomService.checkMemberInChatroom(chatCreateRequest.getMemberId(), chatCreateRequest.getChatroomId());

		// RabbitMQ 로 메시지 전송
		chatProducer.produceChatMessage(chatCreateRequest);

		// 응답 객체 생성
		return new ChatCreate.Response(
			chatCreateRequest.getMemberId(),
			chatCreateRequest.getChatroomId(),
			memberService.getNickname(chatCreateRequest.getMemberId()),
			chatCreateRequest.getContent(),
			chatCreateRequest.getMessageType(),
			chatCreateRequest.getCreatedAt()
		);
	}

	@Override
	public void removeChat() {

	}

	@Override
	public List<ChatRead.Response> getChats(ChatQueryRequest chatQueryRequest) {
		String key = "chatroom:" + chatQueryRequest.getChatroomId();

		// Redis에서 메시지 조회
		Set<String> redisMessages = (chatQueryRequest.getLastId() == null)
			? fetchRecentMessagesFromRedis(key, chatQueryRequest.getLimit()) // 최근 메시지 조회
			: fetchMessagesFromRedis(key, chatQueryRequest); // 특정 위치부터 메시지 조회

		// Redis 데이터 파싱
		List<ChatRead.Response> chats = parseChats(redisMessages);

		// 요청한 개수보다 적게 조회된 경우 DB에서 나머지 데이터 가져오기
		if (chats.size() < chatQueryRequest.getLimit()) {
			appendMessagesFromDb(chatQueryRequest, chats);
		}

		return chats;
	}

	private void appendMessagesFromDb(ChatQueryRequest chatQueryRequest, List<ChatRead.Response> chats) {
		int remainingSize = chatQueryRequest.getLimit() - chats.size();
		if (remainingSize <= 0) {
			return;
		}

		// 마지막 메시지의 createdAte과 id를 기준으로 DB 조회 준비
		Long dbLastId = chats.isEmpty() ? chatQueryRequest.getLastId() : chats.getLast().getId();

		// DB 조회 요청 생성
		ChatQueryRequest dbRequest = new ChatQueryRequest(chatQueryRequest.getChatroomId(), dbLastId, remainingSize);
		List<Chat> dbMessages = chatRepository.findNextChats(dbRequest);

		// DB 결과를 DTO로 변환 후 추가
		List<ChatRead.Response> dbMessagesDto = dbMessages.stream()
			.map(chat -> new ChatRead.Response(
				chat.getId(),
				chat.getChatroomId(),
				chat.getMemberId(),
				memberService.getNickname(chat.getMemberId()),
				chat.getContent(),
				chat.getMessageType(),
				chat.getCreatedAt()
			))
			.toList();

		chats.addAll(dbMessagesDto);
	}

	private Set<String> fetchRecentMessagesFromRedis(String key, int limit) {
		return redisTemplate.opsForZSet().reverseRange(key, 0, limit - 1);
	}

	private Set<String> fetchMessagesFromRedis(String key, ChatQueryRequest chatQueryRequest) {
		double score = chatQueryRequest.getLastId();
		return redisTemplate.opsForZSet()
			.reverseRangeByScore(key, Double.NEGATIVE_INFINITY, score - 1, 0, chatQueryRequest.getLimit());
	}

	private List<ChatRead.Response> parseChats(Set<String> chatMessages) {
		return chatMessages.stream()
			.map(json -> {
				try {
					return objectMapper.readValue(json, ChatRead.Response.class);
				} catch (JsonProcessingException e) {
					log.warn("채팅 메시지 역직렬화 실패 - 오류: {}", json, e);
					return null;
				}
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}
}

