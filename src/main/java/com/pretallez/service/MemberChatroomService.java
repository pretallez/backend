package com.pretallez.service;

import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.entity.Chatroom;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.MemberChatroom;

public interface MemberChatroomService {

    /**
     * 회원을 채팅방에 참가시킵니다.
     * @param memberChatroomCreateRequest 회원 채팅방 참가 요청 데이터
     * @return 회원 채팅방 참가 결과 데이터
     */
    MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest);

    /**
     * 회원을 채팅방에서 퇴장시킵니다.
     * @param memberChatroomDeleteRequest 회원 채팅방 퇴장 요청 데이터
     */
    void removeMemberFromChatroom(MemberChatroomDelete.Request memberChatroomDeleteRequest);

    /**
     * MemberChatroom 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다.
     * @param member Member 엔티티
     * @param chatroom Chatroom 엔티티
     * @return MemberChatroom 엔티티
     */
    MemberChatroom getMemberChatroomOrThrow(Member member, Chatroom chatroom);
}
