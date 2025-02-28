package com.pretallez.repository;

import com.pretallez.model.entity.MemberChatroom;

import java.util.Optional;

public interface MemberChatroomRepository {

    MemberChatroom save(MemberChatroom memberChatroom);
    void delete(MemberChatroom memberChatroom);
    Optional<MemberChatroom> findById(Long id);
}
