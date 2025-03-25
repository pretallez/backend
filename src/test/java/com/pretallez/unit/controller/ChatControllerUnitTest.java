package com.pretallez.unit.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pretallez.common.fixture.ChatFixture;
import com.pretallez.controller.ChatController;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.dto.ChatQueryRequest;
import com.pretallez.domain.chat.dto.ChatRead;
import com.pretallez.domain.chat.service.ChatService;

@WebMvcTest(ChatController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@DisplayName("채팅 컨트롤러 단위 테스트")
class ChatControllerUnitTest {

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ChatService chatService;

	@MockitoBean
	private SimpMessagingTemplate messagingTemplate;

	@Test
	@DisplayName("채팅 메시지 저장 시, 성공 및 200 응답")
	void WhenCreateChat_ThenReturnSuccess_200() throws Exception {
		// Given
		ChatCreate.Request request = ChatFixture.chatCreateReqeust();
		ChatCreate.Response response = ChatFixture.chatCreateResponse();

		String requestBody = objectMapper.writeValueAsString(request);

		when(chatService.addChat(any())).thenReturn(response);

		// When & Then
		mockMvc.perform(post("/v1/api/chats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andDo(document("chats/create",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("memberId").description("회원 ID"),
					fieldWithPath("chatroomId").description("채팅방 ID"),
					fieldWithPath("content").description("채팅 메시지"),
					fieldWithPath("messageType").description("메시지 타입 (CHAT)"),
					fieldWithPath("createdAt").description("메시지 생성 시간 (백엔드 자동 생성)")
				),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지")
				)
			));

		verify(chatService, times(1)).addChat(any());
	}

	@Test
	@DisplayName("채팅 메시지 조회 시, 성공 및 200 응답")
	void WhenReadChat_ThenReturnSuccess_200() throws Exception {
		// Given
		ChatQueryRequest chatQueryRequest = ChatFixture.chatQueryRequest();
		List<ChatRead.Response> responses = ChatFixture.chatReadResponses();
		String requestBody = objectMapper.writeValueAsString(chatQueryRequest);

		when(chatService.getChats(any())).thenReturn(responses);

		// When & Then
		mockMvc.perform(get("/v1/api/chats")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andDo(document("chats/read",
				Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
				Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
				requestFields(
					fieldWithPath("chatroomId").description("채팅방 ID"),
					fieldWithPath("lastCreatedAt").description("메시지 생성날짜"),
					fieldWithPath("lastId").description("메시지 ID"),
					fieldWithPath("limit").description("메시지 조회 개수")
				),
				responseFields(
					fieldWithPath("httpStatusCode").description("HTTP 상태 코드"),
					fieldWithPath("code").description("응답 코드"),
					fieldWithPath("message").description("응답 메시지"),
					fieldWithPath("data").description("응답 데이터 객체"),
					fieldWithPath("data[].id").description("메시지 ID"),
					fieldWithPath("data[].chatroomId").description("채팅방 ID"),
					fieldWithPath("data[].memberId").description("회원 ID"),
					fieldWithPath("data[].nickname").description("회원 닉네임"),
					fieldWithPath("data[].content").description("메시지 내용"),
					fieldWithPath("data[].messageType").description("메시지 타입 (CHAT, ENTER, EXIT)"),
					fieldWithPath("data[].createdAt").description("메시지 생성 시간")
				)
			));

		verify(chatService, times(1)).getChats(any());
	}
}