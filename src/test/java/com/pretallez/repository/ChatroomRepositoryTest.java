package com.pretallez.repository;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.entity.*;
import com.pretallez.repository.jpa.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class ChatroomRepositoryTest {

    private final ChatroomJpaRepository chatroomRepository;
    private final VotePostJpaRepository votePostRepository;
    private final BoardJpaRepository boardRepository;
    private final FencingClubJpaRepository fencingClubRepository;
    private final MemberJpaRepository memberRepository;

    private VotePost savedVotePost;

    @Autowired
    private ChatroomRepositoryTest(
            ChatroomJpaRepository chatroomRepository,
            VotePostJpaRepository votePostRepository,
            BoardJpaRepository boardRepository,
            FencingClubJpaRepository fencingClubRepository,
            MemberJpaRepository memberRepository
    ) {
        this.chatroomRepository =chatroomRepository;
        this.votePostRepository = votePostRepository;
        this.boardRepository = boardRepository;
        this.fencingClubRepository = fencingClubRepository;
        this.memberRepository = memberRepository;
    }

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