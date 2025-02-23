package com.pretallez.repository.jpa;

import com.pretallez.model.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatroomJpaRepository extends JpaRepository<Chatroom, Long> {

    boolean existsByVotePostId(Long votePostId);
}
