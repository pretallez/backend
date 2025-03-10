package com.pretallez.repository;

import com.pretallez.model.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.model.dto.memberchatroom.MemberChatroomsRead;
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
    List<MemberChatroomsRead.Response> findChatroomsByMemberId(Long memberId);
    List<ChatroomMembersRead.Response> findMembersByChatroomId(Long chatroomId);
}
