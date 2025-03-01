package com.pretallez.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.service.MemberChatroomService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberChatroomControllerUnitTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private MemberChatroomService MemberChatroomService;

    @InjectMocks
    private MemberChatroomController memberChatroomController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberChatroomController).build();
    }

    @Test
    @DisplayName("채팅방 참가 요청 시, 성공 및 200 응답")
    void WhenEnterRequest_ThenReturnSuccess_200() throws Exception {
        // Given
        Long id = 1L;
        Long memberId = 1L;
        Long chatroomId = 1L;
        MemberChatroomCreate.Request request = Fixture.memberChatroomCreateRequest(memberId, chatroomId);
        MemberChatroomCreate.Response response = Fixture.memberChatroomCreateResponse(id, memberId, chatroomId);

        String requestBody = objectMapper.writeValueAsString(request);

        when(MemberChatroomService.addMemberToChatroom(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.memberId").value(chatroomId));

        verify(MemberChatroomService, times(1)).addMemberToChatroom(any());
    }

    @Test
    @DisplayName("채팅방 퇴장 요청 시, 성공 및 200 응답")
    void WhenExitRequest_ThenReturnSuccess_200() throws Exception {
        // Given
        Long id = 1L;
        Long memberId = 1L;
        Long chatroomId = 1L;
        MemberChatroomDelete.Request request = Fixture.memberChatroomDeleteRequest(memberId, chatroomId);

        String requestBody = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(delete("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(MemberChatroomService, times(1)).removeMemberFromChatroom(any());
    }
}