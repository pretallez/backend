package com.pretallez.service;

import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.dto.memberchatroom.MemberChatroomRead;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;

import java.util.List;

public interface MemberChatroomService {

    /** MemberChatroom 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    MemberChatroom getMemberChatroom(Member member, Chatroom chatroom);

    /** 회원을 채팅방에 참가시킵니다. */
    MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest);

    /** 회원을 채팅방에서 퇴장시킵니다. */
    void removeMemberFromChatroom(MemberChatroomDelete.Request memberChatroomDeleteRequest);

    /** 회원의 채팅방을 모두 조회합니다. */
    void getChatrooms();

    /** 현재 채팅방의 모든 회원을 조회합니다. */
    List<MemberChatroomRead.Response> getMembers(MemberChatroomRead.Request memberChatroomReadRequest);
}