package com.pretallez.domain.chatting.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatQueryRequest {
	private Long chatroomId;
	private Long lastId;
	private int limit;
}
