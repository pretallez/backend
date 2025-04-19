package com.pretallez.domain.chatting.repository.chat;

import java.util.List;

import com.pretallez.domain.chatting.entity.Chat;
import com.pretallez.domain.chatting.dto.chat.ChatQueryRequest;

public interface ChatRepository {

	Chat save(Chat chat);
	List<Chat> saveAll(List<Chat> chats);
	List<Chat> findAll();
	List<Chat> findNextChats(ChatQueryRequest chatQueryRequest);
}
