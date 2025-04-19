package com.pretallez.integration.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.common.fixture.ChatroomFixture;
import com.pretallez.common.fixture.TestFixtureFactory;
import com.pretallez.domain.chatting.dto.chatroom.ChatroomCreate;
import com.pretallez.domain.chatting.service.chatroom.ChatroomServiceImpl;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("채팅방 서비스 통합 테스트")
class ChatroomServiceIntegrationTest {

    @Autowired
    private ChatroomServiceImpl chatroomService;

    @Autowired
    private TestFixtureFactory testFixtureFactory;

    private VotePost savedVotePost;

    @BeforeEach
    void setUp() {
        savedVotePost = testFixtureFactory.createVotePost();
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