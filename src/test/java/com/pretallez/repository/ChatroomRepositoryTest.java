package com.pretallez.repository;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("com.pretallez.repository.impls")
@ActiveProfiles("local")
class ChatroomRepositoryTest {

    @Autowired
    private  ChatroomRepository chatroomRepository;

    @Autowired
    private VotePostRepository votePostRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FencingClubRepository fencingClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    private VotePost savedVotePost;

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        savedVotePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));
    }

    @Test
    @DisplayName("Chatroom 저장 시 성공")
    void save_WhenCreateChatroom_ThenReturnSuccess() {
        // Given &  When
        Chatroom savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // Then
        assertThat(savedChatroom.getId()).isNotNull();
        assertThat(savedChatroom.getVotePost().getId()).isEqualTo(savedVotePost.getId());
    }

    @Test
    @DisplayName("이미 생성된 VotePost로 Chatroom을 저장 시, DataIntegrityViolation 예외 발생")
    void save_WhenCreateChatroomWithAlreadyCreatedVotePost_ThenReturnFail_DataIntegrityViolationException() {
        // Given
        chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            chatroomRepository.save(Fixture.chatroom(savedVotePost));
        });
    }
}