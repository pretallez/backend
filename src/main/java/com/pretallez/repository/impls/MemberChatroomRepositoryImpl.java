package com.pretallez.repository.impls;

import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.repository.MemberChatroomRepository;
import com.pretallez.repository.jpa.MemberChatroomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberChatroomRepositoryImpl implements MemberChatroomRepository {

    private final MemberChatroomJpaRepository memberChatroomJpaRepository;

    @Override
    public MemberChatroom save(MemberChatroom memberChatroom) {
        return memberChatroomJpaRepository.save(memberChatroom);
    }

    @Override
    public void delete(MemberChatroom memberChatroom) {
        memberChatroomJpaRepository.delete(memberChatroom);
    }

    @Override
    public Optional<MemberChatroom> findById(Long id) {
        return memberChatroomJpaRepository.findById(id);
    }

    @Override
    public Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom) {
        return memberChatroomJpaRepository.findByMemberAndChatroom(member, chatroom);
    }
}
