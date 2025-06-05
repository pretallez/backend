package com.pretallez.unit.chat.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.application.chat.dto.ChatMessageRequest;
import com.pretallez.application.chat.dto.ChatMessageResponse;
import com.pretallez.application.chat.port.input.ChatMessageUseCase;
import com.pretallez.controller.ChatRestController;
import com.pretallez.domain.chat.vo.MessageType;

@ExtendWith(RestDocumentationExtension.class)
class ChatControllerUnitTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private ChatMessageUseCase chatMessageUseCase;

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) {
		chatMessageUseCase = mock(ChatMessageUseCase.class);
		objectMapper = new ObjectMapper();

		ChatRestController sut = new ChatRestController(chatMessageUseCase);
		mockMvc = MockMvcBuilders.standaloneSetup(sut)
			.apply(documentationConfiguration(restDocumentation))
			.build();
	}

	@Test
	void 채팅_메시지를_보내면_정상적으로_저장된다() throws Exception {
		// given
		ChatMessageRequest request = new ChatMessageRequest(1L, 2L, "Hello", MessageType.CHAT);

		// when
		mockMvc.perform(post("/v1/api/chats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())

			// then
			.andDo(document("chats/create",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("senderId").description("회원 ID"),
					fieldWithPath("chatRoomId").description("채팅방 ID"),
					fieldWithPath("content").description("채팅 메시지"),
					fieldWithPath("messageType").description("메시지 타입")
				),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("description").description("응답 메시지 설명")
				)
			));

		verify(chatMessageUseCase).sendMessage(request);
	}

	@Test
	void 채팅_메시지를_조회하면_최근_메시지들이_반환된다() throws Exception {
		// given
		Long chatRoomId = 1L;
		Long lastMessageId = 3L;
		int size = 2;

		List<ChatMessageResponse> responses = List.of(
			new ChatMessageResponse(1L, chatRoomId, 1L, "임종엽", "채팅1", LocalDateTime.now()),
			new ChatMessageResponse(2L, chatRoomId, 2L, "김성호", "채팅2", LocalDateTime.now())
		);

		// when
		when(chatMessageUseCase.getRecentMessages(chatRoomId, lastMessageId, size))
			.thenReturn(responses);

		mockMvc.perform(get("/v1/api/chats")
				.param("chatRoomId", chatRoomId.toString())
				.param("lastMessageId", lastMessageId.toString())
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk())

			// then
			.andDo(document("chats/read",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("description").description("응답 메시지 설명"),
					fieldWithPath("data").description("채팅 메시지 리스트"),
					fieldWithPath("data[].id").description("채팅 메시지 ID"),
					fieldWithPath("data[].chatRoomId").description("채팅방 ID"),
					fieldWithPath("data[].senderId").description("보낸 회원 ID"),
					fieldWithPath("data[].senderNickname").description("보낸 회원 닉네임"),
					fieldWithPath("data[].content").description("채팅 메시지"),
					fieldWithPath("data[].createdAt").description("생성 시간").optional()
				)
			));

		verify(chatMessageUseCase).getRecentMessages(chatRoomId, lastMessageId, size);
	}

}