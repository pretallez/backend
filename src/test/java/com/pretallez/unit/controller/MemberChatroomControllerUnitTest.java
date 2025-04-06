package com.pretallez.unit.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.MemberChatroomFixture;
import com.pretallez.common.response.success.ResSuccessCode;
import com.pretallez.controller.MemberChatroomController;
import com.pretallez.domain.memberchatroom.dto.ChatroomMembersRead;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomCreate;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomDelete;
import com.pretallez.domain.memberchatroom.dto.MemberChatroomsRead;
import com.pretallez.domain.memberchatroom.service.MemberChatroomService;

@ExtendWith(MockitoExtension.class)
@DisplayName("회원 채팅방 컨트롤러 단위 테스트")
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
        MemberChatroomCreate.Request request = MemberChatroomFixture.memberChatroomCreateRequest(memberId, chatroomId);
        MemberChatroomCreate.Response response = MemberChatroomFixture.memberChatroomCreateResponse(id, memberId, chatroomId);

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
        MemberChatroomDelete.Request request = MemberChatroomFixture.memberChatroomDeleteRequest(1L, 1L);

        String requestBody = objectMapper.writeValueAsString(request);

        // When & Then
        mockMvc.perform(delete("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(MemberChatroomService, times(1)).removeMemberFromChatroom(any());
    }

    @Test
    @DisplayName("회원의 채팅방 조회 요청 시, 성공 및 200 응답")
    void WhenReadMemberChatroom_ThenReturnSuccess_200() throws Exception {
        // Given
        Long memberId = 1L;

        List<MemberChatroomsRead.Response> responses = MemberChatroomFixture.memberChatroomsReadResponses();
        when(MemberChatroomService.getMemberChatrooms(any())).thenReturn(responses);

        // When & Then
        mockMvc.perform(get("/v1/api/chatrooms/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResSuccessCode.READ.getCode()))
                .andExpect(jsonPath("$.message").value("READ"))
                .andExpect(jsonPath("$.description").value("Read Successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].votePostId").value(1))
                .andExpect(jsonPath("$.data[0].title").value("첫 번째 채팅방의 투표글 제목"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].votePostId").value(2))
                .andExpect(jsonPath("$.data[1].title").value("두 번째 채팅방의 투표글 제목"));

        verify(MemberChatroomService, times(1)).getMemberChatrooms(any());
    }

    @Test
    @DisplayName("채팅방의 회원 조회 요청 시, 성공 및 200 응답")
    void WhenReadChatroomMembers_ThenReturnSuccess_200() throws Exception {
        // Given
        Long chatroomId = 1L;

        List<ChatroomMembersRead.Response> responses = MemberChatroomFixture.chatroomMembersReadResponses();
        when(MemberChatroomService.getChatroomMembers(any())).thenReturn(responses);

        // When & Then
        mockMvc.perform(get("/v1/api/chatrooms/" + chatroomId + "/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResSuccessCode.READ.getCode()))
                .andExpect(jsonPath("$.message").value("READ"))
                .andExpect(jsonPath("$.description").value("Read Successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].nickname").value("임종엽"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].nickname").value("김성호"));

        verify(MemberChatroomService, times(1)).getChatroomMembers(any());
    }
}