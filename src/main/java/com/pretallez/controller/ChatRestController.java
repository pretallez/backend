package com.pretallez.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.application.chat.dto.ChatMessageRequest;
import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.input.ChatMessageUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/chats")
@CrossOrigin(origins = "*")
public class ChatRestController {

	private final ChatMessageUseCase messageUseCase;

	@MessageMapping("/v1/api/chats")
	@PostMapping
	public CustomApiResponse<Void> sendMessage(@RequestBody ChatMessageRequest chatMessageDTO) {
		messageUseCase.sendMessage(chatMessageDTO);
		return CustomApiResponse.OK(ResSuccessCode.CREATED);
	}

	@GetMapping
	public CustomApiResponse<List<ChatMessageResponse>> getRecentMessages(
		@RequestParam(name = "chatRoomId") Long chatRoomId,
		@RequestParam(name = "lastMessageId", required = false) Long lastMessageId,
		@RequestParam(name = "size") Integer size
	) {
		List<ChatMessageResponse> recentMessages = messageUseCase.getRecentMessages(
			chatRoomId,
			lastMessageId,
			size
		);

		return CustomApiResponse.OK(ResSuccessCode.READ, recentMessages);
	}
}
