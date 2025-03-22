package com.pretallez.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.domain.memberchatroom.dto.ChatroomMembersRead;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomCreate;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomDelete;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomsRead;
import com.pretallez.domain.memberchatroom.service.MemberChatroomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatrooms")
public class MemberChatroomController {

    private final MemberChatroomService memberChatroomService;

    @PostMapping("/members")
    public CustomApiResponse<MemberChatroomCreate.Response> addMemberToChatroom(@Valid @RequestBody MemberChatroomCreate.Request memberChatroomCreateRequest) {
        MemberChatroomCreate.Response memberChatroomCreateResponse = memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);
        return CustomApiResponse.OK(ResSuccessCode.CREATED, memberChatroomCreateResponse);
    }

    @DeleteMapping("/members")
    public CustomApiResponse<Void> removeMemberFromChatroom(@Valid @RequestBody MemberChatroomDelete.Request memberChatroomDeleteRequest) {
        memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        return CustomApiResponse.OK(ResSuccessCode.DELETED);
    }

    @GetMapping("/members/{memberId}")
    public CustomApiResponse<List<MemberChatroomsRead.Response>> getMemberChatrooms(@PathVariable("memberId") Long memberId) {
        List<MemberChatroomsRead.Response> memberChatroomsReadResponses = memberChatroomService.getMemberChatrooms(memberId);
        return CustomApiResponse.OK(ResSuccessCode.READ, memberChatroomsReadResponses);
    }

	@GetMapping("/{chatroomId}/members")
    public CustomApiResponse<List<ChatroomMembersRead.Response>> getChatroomMembers(@PathVariable("chatroomId") Long chatroomId) {
		List<ChatroomMembersRead.Response> chatroomMembersReadResponses = memberChatroomService.getChatroomMembers(chatroomId);
		return CustomApiResponse.OK(ResSuccessCode.READ, chatroomMembersReadResponses);
	}
}
