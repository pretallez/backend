package com.pretallez.application.chat.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.application.chat.dto.ChatMessageRequest;
import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.input.ChatMessageUseCase;
import com.pretallez.application.chat.port.output.ChatNicknameProvider;
import com.pretallez.domain.chat.entity.ChatMessage;
import com.pretallez.domain.chat.policy.ParticipationPolicy;
import com.pretallez.domain.chat.repository.ChatMessageRepository;
import com.pretallez.infrastructure.chat.broadcaster.ChatMessageBroadcasterExecutor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService implements ChatMessageUseCase {

	private final ParticipationPolicy participantPolicy;
	private final ChatMessageRepository chatMessageRepository;
	private final ChatNicknameProvider nicknameProvider;
	private final ChatMessageBroadcasterExecutor broadcasterExecutor;

	@Transactional
	@Override
	public void sendMessage(ChatMessageRequest request) {
		Long senderId = request.senderId();
		Long chatRoomId = request.chatRoomId();

		participantPolicy.assertParticipating(senderId, chatRoomId);
		ChatMessage message = chatMessageRepository.save(toEntity(request));

		String nickname = nicknameProvider
			.getNicknames(Set.of(message.getSenderId()))
			.get(message.getSenderId());

		ChatMessageResponse response = new ChatMessageResponse(
			message.getId(),
			message.getChatRoomId(),
			message.getSenderId(),
			nickname,
			message.getContent(),
			message.getCreatedAt()
		);

		broadcasterExecutor.broadcast(response);
	}

	@Override
	public List<ChatMessageResponse> getRecentMessages(Long chatRoomId, Long lastMessageId, int size) {
		List<ChatMessage> messages = chatMessageRepository.findRecentMessages(chatRoomId, lastMessageId, size);

		Map<Long, String> nicknameMap = nicknameProvider.getNicknames(
			messages.stream()
				.map(ChatMessage::getSenderId)
				.collect(toSet())
		);

		return messages.stream()
			.map(msg -> toResponse(msg, nicknameMap))
			.collect(toList());
	}

	private ChatMessageResponse toResponse(ChatMessage message, Map<Long, String> nicknameMap) {
		return new ChatMessageResponse(
			message.getId(),
			message.getChatRoomId(),
			message.getSenderId(),
			nicknameMap.get(message.getSenderId()),
			message.getContent(),
			message.getCreatedAt()
		);
	}

	private ChatMessage toEntity(ChatMessageRequest request) {
		return ChatMessage.createMessage(
			request.senderId(),
			request.chatRoomId(),
			request.content(),
			request.messageType()
		);
	}
}
