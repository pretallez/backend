package com.pretallez.repository.impls;

import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.repository.MemberChatroomRepository;
import com.pretallez.repository.jpa.MemberChatroomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberChatroomRepositoryImpl implements MemberChatroomRepository {

    private final MemberChatroomJpaRepository memberChatroomJpaRepository;

    @Override
    public MemberChatroom save(MemberChatroom memberChatroom) {
        return memberChatroomJpaRepository.save(memberChatroom);
    }
}
