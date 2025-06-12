package com.pretallez.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.pretallez.application.chat.dto.ChatMessageRequest;
import com.pretallez.application.chat.port.input.ChatMessageUseCase;
import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatWebSocketController {

	private final ChatMessageUseCase messageUseCase;

	@MessageMapping("/v1/api/chats")
	public CustomApiResponse<Void> sendMessage(@RequestBody ChatMessageRequest chatMessageDTO) {
		messageUseCase.sendMessage(chatMessageDTO);
		return CustomApiResponse.OK(ResSuccessCode.CREATED);
	}
}
