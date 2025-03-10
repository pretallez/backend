package com.pretallez.repository;

import com.pretallez.model.dto.memberchatroom.MemberChatroomRead;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomRepository {

    MemberChatroom save(MemberChatroom memberChatroom);
    void delete(MemberChatroom memberChatroom);
    Optional<MemberChatroom> findById(Long id);

    /** Query Method */
    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);

    /** QueryDSL */
    /** QueryDSL을 사용하여 MemberChatroom 엔티티와 관련된 Chatroom, VotePost, Board 테이블을 조인하여 한 번의 쿼리로 데이터를 가져온다. */
    List<MemberChatroomRead.Response> findByMemberWithChatroomAndBoard(Long memberId);
}
