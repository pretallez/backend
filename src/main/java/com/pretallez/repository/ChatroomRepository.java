package com.pretallez.repository;

import com.pretallez.model.entity.Chatroom;

import java.util.Optional;

public interface ChatroomRepository {

    Chatroom save(Chatroom chatroom);
    Optional<Chatroom> findById(Long id);
}
