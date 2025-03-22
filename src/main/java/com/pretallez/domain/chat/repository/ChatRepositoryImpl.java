package com.pretallez.domain.chat.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pretallez.common.entity.Chat;
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
}
