package com.pretallez.service;

import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;

public interface ChatroomService {

    /**
     * 채팅방을 생성합니다.
     * @param chatroomCreateRequest 채팅방 생성 요청 데이터
     * @return 채팅방 생성 결과 데이터
     */
    ChatroomCreate.Response addChatroom(ChatroomCreate.Request chatroomCreateRequest);

    /**
     * 회원을 채팅방에 참가시킵니다.
     * @param memberChatroomCreateRequest 회원 채팅방 참가 요청 데이터
     * @return 회원 채팅방 참가 결과 데이터
     */
    MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest);
}
