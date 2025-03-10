package com.pretallez.integration.service;

import com.pretallez.common.fixture.*;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.*;
import com.pretallez.service.VotePostService;
import com.pretallez.service.impls.ChatroomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("채팅방 서비스 통합 테스트")
class ChatroomServiceIntegrationTest {

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private VotePostRepository votePostRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FencingClubRepository fencingClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VotePostService votePostService;

    @Autowired
    private ChatroomServiceImpl chatroomService;

    private VotePost savedVotePost;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(MemberFixture.member());
        Board savedBoard = boardRepository.save(BoardFixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(FencingClubFixture.fencingClub());
        savedVotePost = votePostRepository.save(VotePostFixture.votePost(savedBoard, savedFencingClub));
    }

    @Test
    @DisplayName("채팅방 생성 시, 성공")
    void addChatroom_WhenCreateChatroom_ThenReturnSuccess() {
        // Given
        ChatroomCreate.Request request = ChatroomFixture.chatroomCreateRequest(savedVotePost.getId());

        // When
        ChatroomCreate.Response response = chatroomService.addChatroom(request);

        // Then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getVotePostId()).isEqualTo(savedVotePost.getId());
    }

    @Test
    @DisplayName("이미 생성된 투표글ID로 채팅방 생성 시, DataIntegrityVioltation 예외 발생")
    void addChatroom_WhenCreateWithAlreadyCreatedVotePost_ThenReturnFail_DataIntegrityViolation() {
        // Given
        ChatroomCreate.Request request = ChatroomFixture.chatroomCreateRequest(savedVotePost.getId());

        // When
        chatroomService.addChatroom(request);

        // Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            chatroomService.addChatroom(request);
        });
    }
}