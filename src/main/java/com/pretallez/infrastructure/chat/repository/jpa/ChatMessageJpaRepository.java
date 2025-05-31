package com.pretallez.infrastructure.chat.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pretallez.domain.chat.entity.ChatMessage;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
}
