package com.pretallez.controller.api;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.dto.memberchatroom.MemberChatroomRead;
import com.pretallez.service.MemberChatroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatrooms/members")
public class MemberChatroomController {

    private final MemberChatroomService memberChatroomService;

    @PostMapping
    public CustomApiResponse<MemberChatroomCreate.Response> addMemberToChatroom(@Valid @RequestBody MemberChatroomCreate.Request memberChatroomCreateRequest) {
        MemberChatroomCreate.Response memberChatroomCreateResponse = memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, memberChatroomCreateResponse);
    }

    @DeleteMapping
    public CustomApiResponse<Void> removeMemberFromChatroom(@Valid @RequestBody MemberChatroomDelete.Request memberChatroomDeleteRequest) {
        memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        return CustomApiResponse.OK(ResSuccessCode.DELETED);
    }

    @GetMapping
    public CustomApiResponse<List<MemberChatroomRead.Response>> getMembers(MemberChatroomRead.Request memberChatroomReadReqeust) {
        List<MemberChatroomRead.Response> memberChatroomReadResponses = memberChatroomService.getMembers(memberChatroomReadReqeust);
        return CustomApiResponse.OK(ResSuccessCode.READ, memberChatroomReadResponses);
    }
}
