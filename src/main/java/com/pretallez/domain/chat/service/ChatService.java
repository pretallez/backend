package com.pretallez.domain.chat.service;

public interface ChatService {

    /** 채팅을 저장합니다. */
    void addChat();

    /** 특정 채팅방의 모든 채팅을 삭제합니다. */
    void removeChat();

    /** 특정 채팅방의 모든 채팅을 조회합니다. (페이징 처리) */
    void getChats();
}
