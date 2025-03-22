package com.pretallez.domain.chatroom.service;

import com.pretallez.domain.chatroom.dto.ChatroomCreate;
import com.pretallez.common.entity.Chatroom;

public interface ChatroomService {

    /** Chatroom 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    Chatroom getChatroom(Long chatroomId);

    /** 채팅방을 생성합니다. */
    ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest);

    /** 채팅방을 삭제합니다. */
    void removeChatroom();
}
