package com.pretallez.domain.chat.service;

import java.util.List;

import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatQueryRequest;
import com.pretallez.domain.chat.dto.ChatRead;

public interface ChatService {

    /** 채팅을 저장합니다. */
    ChatCreate.Response addChat(ChatCreate.Request chatCreateRequest);

    /** 특정 채팅방의 모든 채팅을 삭제합니다. */
    void removeChat();

    /** 특정 채팅방의 모든 채팅을 조회합니다. (페이징 처리) */
    List<ChatRead.Response> getChats(ChatQueryRequest chatQueryRequest);
}
