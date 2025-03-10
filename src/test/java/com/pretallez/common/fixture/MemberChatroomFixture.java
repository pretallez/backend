package com.pretallez.common.fixture;

import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.dto.memberchatroom.MemberChatroomsRead;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;
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

    public static MemberChatroomsRead.Request memberChatroomReadRequest(Long memberId) {
        return new MemberChatroomsRead.Request(memberId);
    }

    public static List<MemberChatroomsRead.Response> memberChatroomReadResponses() {
        return List.of(
                new MemberChatroomsRead.Response(1L, 1L, "첫 번째 채팅방의 투표글 제목"),
                new MemberChatroomsRead.Response(2L, 2L, "두 번째 채팅방의 투표글 제목")
        );
    }
}
