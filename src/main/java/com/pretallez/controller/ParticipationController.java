package com.pretallez.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.application.chat.dto.ParticipantResponse;
import com.pretallez.application.chat.dto.ParticipatedChatRoomResponse;
import com.pretallez.application.chat.port.input.ChatRoomParticipationUseCase;
import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chatrooms")
public class ParticipationController {

	private final ChatRoomParticipationUseCase participationUseCase;

	@PostMapping("/{roomId}/members")
	public CustomApiResponse<Void> join(@PathVariable(name = "roomId") Long roomId, @RequestParam(name = "memberId") Long memberId) {
		participationUseCase.join(memberId, roomId);
		return CustomApiResponse.OK(ResSuccessCode.CREATED);
	}

	@GetMapping("/{roomId}/members")
	public CustomApiResponse<List<ParticipantResponse>> getParticipants(@PathVariable(name = "roomId") Long roomId) {
		List<ParticipantResponse> participants = participationUseCase.getParticipants(roomId);
		return CustomApiResponse.OK(ResSuccessCode.READ, participants);
	}

	@GetMapping("/participated")
	public CustomApiResponse<List<ParticipatedChatRoomResponse>> getParticipatedChatRooms(@RequestParam(name = "memberId") Long memberId) {
		List<ParticipatedChatRoomResponse> participatedChatRooms = participationUseCase.getParticipatedChatRooms(memberId);
		return CustomApiResponse.OK(ResSuccessCode.READ, participatedChatRooms);
	}


}
