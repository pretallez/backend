package com.pretallez.service;

import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Chatroom;

public interface ChatroomService {

    /** Chatroom 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    Chatroom getChatroom(Long chatroomId);

    /** 채팅방을 생성합니다. */
    ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest);

    /** 채팅방을 삭제합니다. */
    void removeChatroom();
}
