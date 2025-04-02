package com.pretallez.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.ResSuccessCode;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatQueryRequest;
import com.pretallez.domain.chat.dto.ChatRead;
import com.pretallez.domain.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin(origins = "*")
@RequestMapping("/v1/api/chats")
public class ChatController {

	private static final String DESTINATION_PREFIX = "/v1/api/chatrooms/";

	private final ChatService chatService;

	@MessageMapping("/v1.api.chats")
	@PostMapping
	public CustomApiResponse<Void> addChats(@RequestBody ChatCreate.Request chatCreateRequest) {
		log.info("채팅 메시지 전송 요청: {}", chatCreateRequest);
		chatService.addChat(chatCreateRequest);

		return CustomApiResponse.OK(ResSuccessCode.CREATED);
	}

	@GetMapping
	public CustomApiResponse<List<ChatRead.Response>> getChats(ChatQueryRequest chatQueryRequest) {
		log.info("채팅 메시지 조회 요청: {}", chatQueryRequest);

		return CustomApiResponse.OK(ResSuccessCode.READ, chatService.getChats(chatQueryRequest));
	}
}
