package com.pretallez.application.chat.dto;

public record ParticipatedChatRoomResponse(
	Long chatRoomId,
	Long votePostId,
	String boardTitle
) { }
