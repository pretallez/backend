package com.pretallez.domain.chatting.repository.memberchatroom;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.domain.chatting.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomsRead;

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
    boolean existsMemberInChatroom(Long memberId, Long chatroomId);
}
