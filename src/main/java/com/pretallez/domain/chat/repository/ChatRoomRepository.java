package com.pretallez.domain.chat.repository;

import java.util.Optional;

import com.pretallez.domain.chat.entity.ChatRoom;

public interface ChatRoomRepository {
	// Command
	void save(ChatRoom chatRoom);
	void delete(ChatRoom chatRoom);

	// Query
	Optional<ChatRoom> findById(Long roomId);
}
