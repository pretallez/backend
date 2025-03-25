package com.pretallez.domain.chat.repository;

import java.util.List;

import com.pretallez.common.entity.Chat;
import com.pretallez.domain.chat.dto.ChatQueryRequest;

public interface ChatRepository {

	Chat save(Chat chat);
	List<Chat> saveAll(List<Chat> chats);
	List<Chat> findAll();
	List<Chat> findNextChats(ChatQueryRequest chatQueryRequest);
}
