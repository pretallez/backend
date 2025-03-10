package com.pretallez.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.BoardRepository;
import com.pretallez.repository.FencingClubRepository;
import com.pretallez.repository.MemberRepository;
import com.pretallez.repository.VotePostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class ChatroomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VotePostRepository votePostRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FencingClubRepository fencingClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long votePostId;
    private Member savedMember;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        VotePost votePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));

        votePostId = votePost.getId();
    }

    @Test
    @DisplayName("채팅방 생성 요청 시, 성공 및 200 응답")
    void WhenCreateRequest_ThenReturnSuccess_200() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.votePostId").value(votePostId));
    }

    @Test
    @DisplayName("투표글ID 없이 채팅방 생성 요청 시, 실패 및 400 응답")
    void WhenCreateRequestWithoutVotePostId_ThenReturnFail_400() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("id", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("생성되지 않은 투표글ID로 채팅방 생성 요청 시, 실패 및 404 응답")
    void createChatroom_notFound() throws Exception {
        // Given
        Long votePostId = -1L;
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("이미 생성된 채팅방 ID로 채팅방 생성 요청 시, 실패 및 409 응답")
    void createChatroom_conflict() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }
}