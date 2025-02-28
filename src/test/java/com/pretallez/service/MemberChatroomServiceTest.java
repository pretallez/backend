package com.pretallez.service;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan("com.pretallez.repository.impls")
@ComponentScan("com.pretallez.service.impls")
@ActiveProfiles("local")
class MemberChatroomServiceTest {

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

    @Autowired
    private ChatroomService chatroomService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberChatroomService memberChatroomService;

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
    @DisplayName("참가되어 있지않은 채팅방 참가 시, 정상적으로 채팅방에 참가된다.")
    void addMemberToChatroom_WhenMemberChatroomNotExists_ThenMemberChatroomIsCreated() {
        // Given
        MemberChatroomCreate.Request memberChatroomCreateRequest = Fixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom.getId());

        // When
        MemberChatroomCreate.Response memberChatroomCreateResponse =  memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        // Given
        assertThat(memberChatroomCreateResponse.getId()).isNotNull();
        assertThat(memberChatroomCreateResponse.getMemberId()).isEqualTo(savedMember.getId());
        assertThat(memberChatroomCreateResponse.getChatroomId()).isEqualTo(savedChatroom.getId());
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 참가 시, 참가가 실패되고 DataIntegrityViolation 예외가 발생한다.")
    void addMemberToChatroom_WhenMemberChatroomExists_ThenThrowDataintegrityViolation() {
        // When
        MemberChatroomCreate.Request memberChatroomCreateRequest = Fixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom.getId());
        memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        // Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);
        });
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 퇴장 시, 정상적으로 퇴장되면 true를 반환한다.")
    void removeMemberFromChatroom_WhenMemberChatroomExists_ThenReturnTrue() {
        // Given
        MemberChatroomCreate.Request memberChatroomCreateRequest = Fixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom.getId());
        memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        MemberChatroomDelete.Request memberChatroomDeleteRequest = Fixture.memberChatroomDeleteRequest(savedMember.getId(), savedChatroom.getId());

        // When
        boolean isDeleted = memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);

        // Then
        assertThat(isDeleted).isTrue();
    }

    @Test
    @DisplayName("참가되어 있지않은 채팅방 퇴장 시, 퇴장이 실패되고 EntityNotFound 예외가 발생한다.")
    void removeMemberFromChatroom_WhenMemberChatroomNotExists_ThenThrowEntityNotFound() {
        // Given
        MemberChatroomDelete.Request memberChatroomDeleteRequest = Fixture.memberChatroomDeleteRequest(savedMember.getId(), savedChatroom.getId());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        });
    }
}