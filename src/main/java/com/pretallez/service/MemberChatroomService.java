package com.pretallez.service;

import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;

public interface MemberChatroomService {

    /**
     * 회원을 채팅방에 참가시킵니다.
     * @param memberChatroomCreateRequest 회원 채팅방 참가 요청 데이터
     * @return 회원 채팅방 참가 결과 데이터
     */
    MemberChatroomCreate.Response addMemberToChatroom(MemberChatroomCreate.Request memberChatroomCreateRequest);
}
