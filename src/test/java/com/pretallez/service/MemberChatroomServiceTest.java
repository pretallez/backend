package com.pretallez.service;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.entity.*;
import com.pretallez.repository.*;
import com.pretallez.service.impls.MemberChatroomServiceImpl;
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
@ComponentScan("com.pretallez.service.impls")
@ActiveProfiles("local")
class MemberChatroomServiceTest {

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

    @Autowired
    private MemberChatroomRepository memberChatroomRepository;

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private MemberService memberService;

    private MemberChatroomService memberChatroomService;
    private Member savedMember;
    private Chatroom savedChatroom;

    @BeforeEach
    void setUp() {
        memberChatroomService = new MemberChatroomServiceImpl(chatroomService, memberService, memberChatroomRepository);

        savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        VotePost savedVotePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));
        savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));
    }

    @Test
    @DisplayName("회원이 채팅방에 정상적으로 참가합니다.")
    void addMemberToChatroom() {
        // Given
        MemberChatroomCreate.Request request = Fixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom.getId());

        // When
        MemberChatroomCreate.Response response = memberChatroomService.addMemberToChatroom(request);

        // Given
        assertThat(response.getId()).isNotNull();
        assertThat(response.getMemberId()).isEqualTo(savedMember.getId());
        assertThat(response.getChatroomId()).isEqualTo(savedChatroom.getId());
    }

    @Test
    @DisplayName("회원이 이미 참가한 채팅방에 참가 시 예외가 발생합니다.")
    void addMemberToChatroom_duplication() {
        // When
        memberChatroomRepository.save(Fixture.memberChatroom(savedMember, savedChatroom));

        // Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            memberChatroomRepository.save(Fixture.memberChatroom(savedMember, savedChatroom));
        });
    }
}