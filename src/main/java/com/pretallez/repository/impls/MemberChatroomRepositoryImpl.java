package com.pretallez.repository.impls;

import com.pretallez.model.dto.memberchatroom.MemberChatroomsRead;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
import com.pretallez.repository.MemberChatroomRepository;
import com.pretallez.repository.jpa.MemberChatroomJpaRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pretallez.model.entity.QBoard.board;
import static com.pretallez.model.entity.QChatroom.chatroom;
import static com.pretallez.model.entity.QMemberChatroom.memberChatroom;
import static com.pretallez.model.entity.QVotePost.votePost;

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
    public List<MemberChatroomsRead.Response> findByMemberWithChatroomAndBoard(Long memberId) {
        return queryFactory
                .select(Projections.constructor(MemberChatroomsRead.Response.class,
                        memberChatroom.id,
                        votePost.id,
                        board.title
                ))
                .from(memberChatroom)
                .join(memberChatroom.chatroom, chatroom)
                .join(chatroom.votePost, votePost)
                .join(votePost.board, board)
                .where(memberChatroom.member.id.eq(memberId))
                .fetch();
    }
}
