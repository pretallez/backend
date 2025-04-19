package com.pretallez.domain.chatting.repository.memberchatroom;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomJpaRepository extends JpaRepository<MemberChatroom, Long> {

    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);
    List<MemberChatroom> findByMember(Member member);
}
