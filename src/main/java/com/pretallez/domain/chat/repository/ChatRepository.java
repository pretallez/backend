package com.pretallez.domain.chat.repository;

import java.util.List;

import com.pretallez.common.entity.Chat;

public interface ChatRepository {
	
	Chat save(Chat chat);
	List<Chat> saveAll(List<Chat> chats);
}
