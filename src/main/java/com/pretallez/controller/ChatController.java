package com.pretallez.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/chats")
public class ChatController {

	private final SimpMessagingTemplate messagingTemplate;
	private final ChatService chatService;

	@MessageMapping("/chats")
	@PostMapping
	public void message(@RequestBody ChatCreate.Request chatCreateRequest) {
		String destination = "/v1/api/chatrooms/" + chatCreateRequest.getChatroomId();
		ChatCreate.Response response = chatService.addChat(chatCreateRequest);
		messagingTemplate.convertAndSend(destination, response);
	}
}
