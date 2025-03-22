package com.pretallez.domain.chatroom.repository;

import com.pretallez.common.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<Chatroom, Long> {

}
