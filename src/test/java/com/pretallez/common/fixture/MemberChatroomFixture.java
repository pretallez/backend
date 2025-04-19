package com.pretallez.common.fixture;


import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.domain.chatting.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomsRead;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

public class MemberChatroomFixture {

    public static MemberChatroom memberChatroom(Member member, Chatroom chatroom) {
        return MemberChatroom.of(member, chatroom);
    }

    public static MemberChatroom fakeMemberChatroom(Long id, Member member, Chatroom chatroom) {
        MemberChatroom memberChatroom = MemberChatroom.of(member, chatroom);
        ReflectionTestUtils.setField(memberChatroom, "id", id);
        return memberChatroom;
    }

    public static MemberChatroomCreate.Request memberChatroomCreateRequest(Long memberId, Long chatroomId) {
        return new MemberChatroomCreate.Request(memberId, chatroomId);
    }

    public static MemberChatroomCreate.Response memberChatroomCreateResponse(Long id, Long memberId, Long chatroomId) {
        return new MemberChatroomCreate.Response(id, memberId, chatroomId);
    }

    public static MemberChatroomDelete.Request memberChatroomDeleteRequest(Long memberId, Long chatroomId) {
        return new MemberChatroomDelete.Request(memberId, chatroomId);
    }

    public static List<MemberChatroomsRead.Response> memberChatroomsReadResponses() {
        return List.of(
                new MemberChatroomsRead.Response(1L, 1L, "첫 번째 채팅방의 투표글 제목"),
                new MemberChatroomsRead.Response(2L, 2L, "두 번째 채팅방의 투표글 제목")
        );
    }

    public static List<ChatroomMembersRead.Response> chatroomMembersReadResponses() {
        return List.of(
            new ChatroomMembersRead.Response(1L, "임종엽"),
            new ChatroomMembersRead.Response(2L, "김성호")
        );
    }
}
