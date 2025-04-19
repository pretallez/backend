package com.pretallez.domain.chatting.repository.chatroom;

import com.pretallez.domain.chatting.entity.Chatroom;

import java.util.Optional;

public interface ChatroomRepository {

    Chatroom save(Chatroom chatroom);
    Optional<Chatroom> findById(Long id);
}
