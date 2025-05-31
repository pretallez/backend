package com.pretallez.infrastructure.chat.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pretallez.domain.chat.entity.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
}
