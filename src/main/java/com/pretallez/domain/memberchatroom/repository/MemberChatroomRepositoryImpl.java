package com.pretallez.domain.memberchatroom.repository;

import com.pretallez.memberchatroom.model.ChatroomMembersRead;
import com.pretallez.memberchatroom.model.MemberChatroomsRead;
import com.pretallez.memberchatroom.repository.MemberChatroomJpaRepository;
import com.pretallez.memberchatroom.repository.MemberChatroomRepository;
import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.MemberChatroom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pretallez.model.entity.QChatroom.chatroom;
import static com.pretallez.model.entity.QMember.member;
import static com.pretallez.model.entity.QMemberChatroom.memberChatroom;

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
}
