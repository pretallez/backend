package com.pretallez.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.service.ChatroomService;
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
    @DisplayName("채팅방 생성 API가 정상적으로 실행됩니다.")
    void createChatroom() throws Exception {
        // Given
        Long id = 1L;
        Long votePostId = 1L;
        ChatroomCreate.Request request = Fixture.chatroomCreateRequest(votePostId);
        ChatroomCreate.Response response = Fixture.chatroomCreateResponse(id, votePostId);

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

    @Test
    @DisplayName("채팅방 참가 API가 정상적으로 실행됩니다.")
    void addMemberToChatroom() throws Exception {
        // Given
        Long id = 1L;
        Long memberId = 1L;
        Long chatroomId = 1L;
        MemberChatroomCreate.Request request = Fixture.memberChatroomCreateRequest(memberId, chatroomId);
        MemberChatroomCreate.Response response = Fixture.memberChatroomCreateResponse(id, memberId, chatroomId);

        String requestBody = objectMapper.writeValueAsString(request);

        when(chatroomService.addMemberToChatroom(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.memberId").value(chatroomId));

        verify(chatroomService, times(1)).addMemberToChatroom(any());
    }

}