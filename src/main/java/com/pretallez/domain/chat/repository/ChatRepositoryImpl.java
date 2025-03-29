package com.pretallez.domain.chat.repository;

import static com.pretallez.common.entity.QChat.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pretallez.common.entity.Chat;
import com.pretallez.domain.chat.dto.ChatQueryRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
			.orderBy(chat.createdAt.desc(), chat.id.desc())
			.limit(chatQueryRequest.getLimit())
			.fetch();
	}

	private BooleanExpression buildPaginationCondition(ChatQueryRequest chatQueryRequest) {
		// 초기 조회 시 모든 데이터 대상
		if (chatQueryRequest.getLastCreatedAt() == null) {
			return null;
		}

		return chat.chatroomId.eq(chatQueryRequest.getChatroomId())
			.and(
				chat.createdAt.lt(chatQueryRequest.getLastCreatedAt())
					.or(chat.createdAt.eq(chatQueryRequest.getLastCreatedAt())
						.and(chat.id.lt(chatQueryRequest.getLastId())))
			);
	}
}
