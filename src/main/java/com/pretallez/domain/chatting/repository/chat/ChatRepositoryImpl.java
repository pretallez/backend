package com.pretallez.domain.chatting.repository.chat;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.chatting.entity.Chat;
import com.pretallez.domain.chatting.dto.chat.ChatQueryRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.pretallez.domain.chatting.entity.QChat.chat;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository{

	private final ChatJpaRepository chatJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public Chat save(Chat chat) {
		return chatJpaRepository.save(chat);
	}

	@Override
	public List<Chat> saveAll(List<Chat> chats) {
		return chatJpaRepository.saveAll(chats);
	}

	@Override
	public List<Chat> findAll() {
		return chatJpaRepository.findAll();
	}

	@Override
	public List<Chat> findNextChats(ChatQueryRequest chatQueryRequest) {
		return queryFactory
			.selectFrom(chat)
			.where(buildPaginationCondition(chatQueryRequest))
			.orderBy(chat.id.desc())
			.limit(chatQueryRequest.getLimit())
			.fetch();
	}

	private BooleanExpression buildPaginationCondition(ChatQueryRequest chatQueryRequest) {
		// 초기 조회 시 모든 데이터 대상
		if (chatQueryRequest.getLastId() == null) {
			return chat.chatroomId.eq(chatQueryRequest.getChatroomId());
		}

		return chat.chatroomId.eq(chatQueryRequest.getChatroomId())
			.and(chat.id.lt(chatQueryRequest.getLastId()));
	}
}
