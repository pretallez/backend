package com.pretallez.repository;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("com.pretallez.repository.impls")
@ActiveProfiles("local")
class MemberChatroomRepositoryTest {

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
    private MemberChatroomRepository memberChatroomRepository;

    private Member savedMember;
    private Chatroom savedChatroom;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        VotePost savedVotePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));
        savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));
    }

    @Test
    @DisplayName("MemberChatroom을 저장합니다.")
    void save() {
        // Given
        MemberChatroom memberChatroom = Fixture.memberChatroom(savedMember, savedChatroom);

        // When
        MemberChatroom savedMemberChatroom = memberChatroomRepository.save(memberChatroom);

        // Then
        assertThat(savedMemberChatroom.getId()).isNotNull();
        assertThat(savedMemberChatroom.getMember()).isEqualTo(savedMember);
        assertThat(savedMemberChatroom.getChatroom()).isEqualTo(savedChatroom);
    }
}