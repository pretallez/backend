package com.pretallez.domain.chatting.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pretallez.domain.chatting.entity.Chat;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
}
