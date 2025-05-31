package com.pretallez.application.chat.dto;

public record ParticipantResponse(
	Long memberId,
	String nickname
) { }