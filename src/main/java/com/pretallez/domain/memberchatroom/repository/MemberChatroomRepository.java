package com.pretallez.domain.memberchatroom.repository;

import com.pretallez.memberchatroom.model.ChatroomMembersRead;
import com.pretallez.memberchatroom.model.MemberChatroomsRead;
import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.MemberChatroom;

import java.util.List;
import java.util.Optional;

public interface MemberChatroomRepository {

    MemberChatroom save(MemberChatroom memberChatroom);
    void delete(MemberChatroom memberChatroom);
    Optional<MemberChatroom> findById(Long id);

    /** Query Method */
    Optional<MemberChatroom> findByMemberAndChatroom(Member member, Chatroom chatroom);

    /** QueryDSL */
    List<MemberChatroomsRead.Response> findChatroomsByMemberId(Long memberId);
    List<ChatroomMembersRead.Response> findMembersByChatroomId(Long chatroomId);
}
