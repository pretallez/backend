package com.pretallez.repository.impls;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.repository.ChatroomRepository;
import com.pretallez.repository.jpa.ChatroomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatroomRepositoryImpl implements ChatroomRepository {

    private final ChatroomJpaRepository chatroomJpaRepository;

    @Override
    public Chatroom save(Chatroom chatroom) {
        return chatroomJpaRepository.save(chatroom);
    }

}
