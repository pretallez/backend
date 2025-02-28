package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.service.MemberChatroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatrooms/members")
public class MemberChatroomController {

    private final MemberChatroomService memberChatroomService;

    /** 채팅방 참가 */
    @PostMapping
    public CustomApiResponse<MemberChatroomCreate.Response> addMemberToChatroom(@Valid @RequestBody MemberChatroomCreate.Request memberChatroomCreateRequest) {
        MemberChatroomCreate.Response memberChatroomCreateResponse = memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, memberChatroomCreateResponse);
    }

    /** 채팅방 퇴장 */
    @DeleteMapping
    public CustomApiResponse<Void> removeMemberFromChatroom(@Valid @RequestBody MemberChatroomDelete.Request memberChatroomDeleteRequest) {
        memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        return CustomApiResponse.OK(ResSuccessCode.DELETED);
    }
}
