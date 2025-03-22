package com.pretallez.domain.memberchatroom.repository;

import static com.pretallez.common.entity.QChatroom.*;
import static com.pretallez.common.entity.QMember.*;
import static com.pretallez.common.entity.QMemberChatroom.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.MemberChatroom;
import com.pretallez.domain.memberchatroom.dto.ChatroomMembersRead;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomsRead;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberChatroomRepositoryImpl implements MemberChatroomRepository {

    private final MemberChatroomJpaRepository memberChatroomJpaRepository;
    private final JPAQueryFactory queryFactory;

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

    @Override
    public List<MemberChatroomsRead.Response> findChatroomsByMemberId(Long memberId) {
        return queryFactory
                .selectDistinct(Projections.constructor(MemberChatroomsRead.Response.class,
                        memberChatroom.id,
                        chatroom.votePost.id,
                        chatroom.boardTitle
                ))
                .from(memberChatroom)
                .join(memberChatroom.chatroom, chatroom)
                .where(memberChatroom.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<ChatroomMembersRead.Response> findMembersByChatroomId(Long chatroomId) {
        return queryFactory
                .select(Projections.constructor(ChatroomMembersRead.Response.class,
                        member.id,
                        member.nickname
                ))
                .from(memberChatroom)
                .join(memberChatroom.member, member)
                .where(memberChatroom.chatroom.id.eq(chatroomId))
                .fetch();
    }

    @Override
    public boolean existsMemberInChatroom(Long memberId, Long chatroomId) {
        return queryFactory.selectOne()
            .from(memberChatroom)
            .where(memberChatroom.member.id.eq(memberId)
                .and(memberChatroom.chatroom.id.eq(chatroomId)))
            .fetchFirst() != null;
    }
}
