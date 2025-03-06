package com.pretallez.repository;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;

import java.util.Optional;

public interface MemberChatroomRepository {

    MemberChatroom save(MemberChatroom memberChatroom);
    void delete(MemberChatroom memberChatroom);
    Optional<MemberChatroom> findById(Long id);
    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);

}
