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
class MemberChatroomControllerTest {

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
    void setUp() {
        savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        VotePost votePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));

        votePostId = votePost.getId();
    }

    @Test
    @DisplayName("채팅방 참가 요청을 하면 채팅방에 정상적으로 참가합니다.")
    void addMemberToChatroom() throws Exception {
        // Given
        String createChatroomRequest = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        String response = mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createChatroomRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long chatroomId = objectMapper.readTree(response).get("data").get("id").asLong();

        String requestBody = objectMapper.writeValueAsString(Map.of(
                "memberId", savedMember.getId(),
                "chatroomId", chatroomId
                ));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.memberId").value(savedMember.getId()))
                .andExpect(jsonPath("$.data.chatroomId").value(chatroomId));
    }

    @Test
    @DisplayName("memberId가 없으면 채팅방 참가에 실패합니다.")
    void addMemberToChatroom_invalid_memberId() throws Exception {
        // Given
        String createChatroomRequest = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        String response = mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createChatroomRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long chatroomId = objectMapper.readTree(response).get("data").get("id").asLong();

        String requestBody = objectMapper.writeValueAsString(Map.of(
                "chatroomId", chatroomId
        ));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("chatroomId가 없으면 채팅방 참가에 실패합니다.")
    void addMemberToChatroom_invalid_chatroomId() throws Exception {
        // Given
        String createChatroomRequest = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        String response = mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createChatroomRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long chatroomId = objectMapper.readTree(response).get("data").get("id").asLong();

        String requestBody = objectMapper.writeValueAsString(Map.of(
                "memberId", savedMember.getId()
        ));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("이미 참가한 채팅방에 중복 참가하면 실패합니다.")
    void addMemberToChatroom_conflict() throws Exception {
        // Given
        String createChatroomRequest = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        String response = mockMvc.perform(post("/v1/api/chatrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createChatroomRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long chatroomId = objectMapper.readTree(response).get("data").get("id").asLong();

        String requestBody = objectMapper.writeValueAsString(Map.of(
                "memberId", savedMember.getId(),
                "chatroomId", chatroomId
        ));

        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.memberId").value(savedMember.getId()))
                .andExpect(jsonPath("$.data.chatroomId").value(chatroomId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatrooms/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }
}