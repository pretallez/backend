package com.pretallez.infrastructure.chat.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.chat.entity.ChatRoom;
import com.pretallez.domain.chat.repository.ChatRoomRepository;
import com.pretallez.infrastructure.chat.repository.jpa.ChatRoomJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

	private final ChatRoomJpaRepository chatRoomJpaRepository;

	@Override
	public void save(ChatRoom chatRoom) {
		chatRoomJpaRepository.save(chatRoom);
	}

	@Override
	public void delete(ChatRoom chatRoom) {
		chatRoomJpaRepository.delete(chatRoom);
	}

	@Override
	public Optional<ChatRoom> findById(Long roomId) {
		return chatRoomJpaRepository.findById(roomId);
	}
}
