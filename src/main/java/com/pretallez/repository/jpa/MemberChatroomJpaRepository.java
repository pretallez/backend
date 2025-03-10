package com.pretallez.repository.jpa;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomJpaRepository extends JpaRepository<MemberChatroom, Long> {

    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);
    List<MemberChatroom> findByMember(Member member);
}
