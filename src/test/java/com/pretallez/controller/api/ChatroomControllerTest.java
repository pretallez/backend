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
@ActiveProfiles("dev")
class ChatroomControllerTest {

    private final MockMvc mockMvc;
    private final VotePostRepository votePostRepository;
    private final BoardRepository boardRepository;
    private final FencingClubRepository fencingClubRepository;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    private Long votePostId;

    @Autowired
    private ChatroomControllerTest(
            MockMvc mockMvc,
            VotePostRepository votePostRepository,
            BoardRepository boardRepository,
            FencingClubRepository fencingClubRepository,
            MemberRepository memberRepository,
            ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.votePostRepository = votePostRepository;
        this.boardRepository = boardRepository;
        this.fencingClubRepository = fencingClubRepository;
        this.memberRepository = memberRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        VotePost votePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));

        votePostId = votePost.getId();
    }

    @Test
    @DisplayName("채팅방 생성 요청을 하면 채팅방이 정상적으로 생성됩니다.")
    void createChatroom() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.votePostId").value(votePostId));
    }

    @Test
    @DisplayName("votePostID가 없으면 채팅방 생성에 실패합니다.")
    void createChatroom_invalid() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("id", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("존재하지 않는 votePostId로 채팅방을 생성하면 실패합니다.")
    void createChatroom_conflict() throws Exception {
        // Given
        Long votePostId = -1L;
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        // When & Then
        mockMvc.perform(post("/v1/api/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("이미 존재하는 votePostId로 채팅방을 생성하면 실패합니다.")
    void createChatroom_notFound() throws Exception {
        // Given
        String requestBody = objectMapper.writeValueAsString(Map.of("votePostId", votePostId));

        mockMvc.perform(post("/v1/api/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        // When & Then
        mockMvc.perform(post("/v1/api/chatroom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }
}