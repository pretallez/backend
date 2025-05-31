package com.pretallez.infrastructure.chat.repository;


import static com.pretallez.domain.chat.entity.QChatMessage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.chat.entity.ChatMessage;
import com.pretallez.domain.chat.repository.ChatMessageRepository;
import com.pretallez.infrastructure.chat.repository.jpa.ChatMessageJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {

	private final ChatMessageJpaRepository chatMessageJpaRepository;
	private final JPAQueryFactory queryFactory;

	public ChatMessage save(ChatMessage chatMessage) {
		return chatMessageJpaRepository.save(chatMessage);
	}

	@Override
	public List<ChatMessage> fetchRecentMessages(Long chatRoomId, Long lastSeenId, int pageSize) {
		return queryFactory
			.selectFrom(chatMessage)
			.where(
				chatMessage.chatRoomId.eq(chatRoomId),
				lastSeenId != null ? chatMessage.id.lt(lastSeenId) : null
			)
			.orderBy(chatMessage.id.desc())
			.limit(pageSize)
			.fetch();
	}
}
