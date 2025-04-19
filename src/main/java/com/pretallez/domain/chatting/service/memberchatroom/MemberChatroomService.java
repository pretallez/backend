package com.pretallez.domain.chatting.service.memberchatroom;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.domain.chatting.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomsRead;

import java.util.List;

public interface MemberChatroomService {

    /** MemberChatroom 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    MemberChatroom getMemberChatroom(Member member, Chatroom chatroom);

    /** 회원을 채팅방에 참가시킵니다. */
    MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest);

    /** 회원을 채팅방에서 퇴장시킵니다. */
    void removeMemberFromChatroom(MemberChatroomDelete.Request memberChatroomDeleteRequest);

    /** 회원의 채팅방을 모두 조회합니다. */
    List<MemberChatroomsRead.Response> getMemberChatrooms(Long memberId);

    /** 현재 채팅방의 모든 회원을 조회합니다. */
    List<ChatroomMembersRead.Response> getChatroomMembers(Long chatroomId);

    /** 회원이 채팅방에 참가되어있는지 확인합니다. */
    boolean checkMemberInChatroom(Long memberId, Long chatroomId);
}