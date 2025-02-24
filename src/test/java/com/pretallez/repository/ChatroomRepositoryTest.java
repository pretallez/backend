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
@ActiveProfiles("dev")
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
    @DisplayName("Chatroom을 저장합니다.")
    void save() {
        // Given &  When
        Chatroom savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // Then
        assertThat(savedChatroom.getId()).isNotNull();
        assertThat(savedChatroom.getVotePost().getId()).isEqualTo(savedVotePost.getId());
    }

    @Test
    @DisplayName("같은 VotePost를 가진 Chatroom을 저장하면 예외가 발생합니다.")
    void save_duplication() {
        // Given
        chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // When & Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            chatroomRepository.save(Fixture.chatroom(savedVotePost));
        });
    }
}