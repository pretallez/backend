package com.pretallez.service;

import com.pretallez.model.dto.chatroom.ChatroomCreate;

public interface ChatroomService {

    ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest);
}
