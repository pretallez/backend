package com.pretallez.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.ChatroomFixture;
import com.pretallez.controller.ChatroomController;
import com.pretallez.domain.chatting.dto.chatroom.ChatroomCreate;
import com.pretallez.domain.chatting.service.chatroom.ChatroomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("채팅방 컨트롤러 단위 테스트")
class ChatroomControllerUnitTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ChatroomService chatroomService;

    @InjectMocks
    private ChatroomController chatroomController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatroomController).build();
    }

    @Test
    @DisplayName("채팅방 생성 요청 시, 성공 및 200 응답")
    void WhenCreateRequest_ThenReturnSuccess_200() throws Exception {
        // Given
        Long id = 1L;
        Long votePostId = 1L;
        ChatroomCreate.Request request = ChatroomFixture.chatroomCreateRequest(votePostId);
        ChatroomCreate.Response response = ChatroomFixture.chatroomCreateResponse(id, votePostId);

        String requestBody = objectMapper.writeValueAsString(request);

        when(chatroomService.addChatroom(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.votePostId").value(votePostId));

        verify(chatroomService, times(1)).addChatroom(any());
    }
}