package com.pretallez.domain.chat.service;

import org.springframework.stereotype.Service;

import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.member.service.MemberService;
import com.pretallez.domain.memberchatroom.service.MemberChatroomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final MemberChatroomService memberChatroomService;
	private final ChatProducer chatProducer;
	private final MemberService memberService;

	@Override
	public ChatCreate.Response addChat(ChatCreate.Request chatCreateRequest) {
		memberChatroomService.checkMemberInChatroom(chatCreateRequest.getMemberId(), chatCreateRequest.getChatroomId());
		chatProducer.produceChatMessage(chatCreateRequest);

		return new ChatCreate.Response(
			chatCreateRequest.getMemberId(),
			chatCreateRequest.getChatroomId(),
			memberService.getNickname(chatCreateRequest.getMemberId()),
			chatCreateRequest.getContent(),
			chatCreateRequest.getMessageType(),
			chatCreateRequest.getCreatedAt()
		);
	}

	@Override
	public void removeChat() {

	}

	@Override
	public void getChats() {

	}
}
