package com.pretallez.domain.chatroom.repository;

import com.pretallez.common.entity.Chatroom;

import java.util.Optional;

public interface ChatroomRepository {

    Chatroom save(Chatroom chatroom);
    Optional<Chatroom> findById(Long id);
}
