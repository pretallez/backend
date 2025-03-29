package com.pretallez.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pretallez.common.entity.Chat;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
}
