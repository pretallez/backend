package com.pretallez.domain.chatroom.repository;

import com.pretallez.common.entity.Chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatroomRepositoryImpl implements ChatroomRepository {

    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public Chatroom save(Chatroom chatroom) {
        return chatroomJpaRepository.save(chatroom);
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        return chatroomJpaRepository.findById(id);
    }
}
