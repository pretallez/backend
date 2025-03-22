package com.pretallez.domain.memberchatroom.repository;

import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.MemberChatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomJpaRepository extends JpaRepository<MemberChatroom, Long> {

    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);
    List<MemberChatroom> findByMember(Member member);
}
