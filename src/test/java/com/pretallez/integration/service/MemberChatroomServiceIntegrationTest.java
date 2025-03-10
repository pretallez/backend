package com.pretallez.integration.service;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.common.fixture.*;
import com.pretallez.model.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.model.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.model.dto.memberchatroom.MemberChatroomsRead;
import com.pretallez.model.entity.*;
import com.pretallez.repository.*;
import com.pretallez.service.ChatroomService;
import com.pretallez.service.MemberChatroomService;
import com.pretallez.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("회원 채팅방 서비스 통합 테스트")
class MemberChatroomServiceIntegrationTest {

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
    private Chatroom savedChatroom1;
    private Chatroom savedChatroom2;

    @BeforeEach
    void setUp() {
        savedMember = memberRepository.save(MemberFixture.member());
        Board savedBoard1 = boardRepository.save(BoardFixture.board(savedMember));
        FencingClub savedFencingClub1 = fencingClubRepository.save(FencingClubFixture.fencingClub());
        VotePost savedVotePost1 = votePostRepository.save(VotePostFixture.votePost(savedBoard1, savedFencingClub1));
        savedChatroom1 = chatroomRepository.save(ChatroomFixture.chatroom(savedVotePost1));

        Board savedBoard2 = boardRepository.save(BoardFixture.board(savedMember));
        FencingClub savedFencingClub2 = fencingClubRepository.save(FencingClubFixture.fencingClub());
        VotePost savedVotePost2 = votePostRepository.save(VotePostFixture.votePost(savedBoard2, savedFencingClub2));
        savedChatroom2 = chatroomRepository.save(ChatroomFixture.chatroom(savedVotePost2));
    }

    @Test
    @DisplayName("채팅방 참가 시, 성공")
    void addMemberToChatroom_WhenMemberChatroomNotExists_ThenMemberChatroomIsCreated() {
        // Given
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom1.getId());

        // When
        MemberChatroomCreate.Response memberChatroomCreateResponse = memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        // Given
        assertThat(memberChatroomCreateResponse.getId()).isNotNull();
        assertThat(memberChatroomCreateResponse.getMemberId()).isEqualTo(savedMember.getId());
        assertThat(memberChatroomCreateResponse.getChatroomId()).isEqualTo(savedChatroom1.getId());
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 참가 시, 참가가 실패되고 DataIntegrityViolation 예외가 발생한다.")
    void addMemberToChatroom_WhenMemberChatroomExists_ThenThrowDataintegrityViolation() {
        // When
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom1.getId());
        memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        // Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);
        });
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 퇴장 시, 정상적으로 채팅방에서 퇴장된다.")
    void removeMemberFromChatroom_WhenMemberChatroomExists_ThenReturnTrue() {
        // Given
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom1.getId());
        memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        MemberChatroomDelete.Request memberChatroomDeleteRequest = MemberChatroomFixture.memberChatroomDeleteRequest(savedMember.getId(), savedChatroom1.getId());

        // When
        memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);

        // Then
        Optional<MemberChatroom> existingMemberChatroom = memberChatroomRepository.findByMemberAndChatroom(savedMember, savedChatroom1);
        assertThat(existingMemberChatroom).isEmpty();
    }

    @Test
    @DisplayName("참가되어 있지않은 채팅방 퇴장 시, 퇴장이 실패되고 EntityNotFound 예외가 발생한다.")
    void removeMemberFromChatroom_WhenMemberChatroomNotExists_ThenThrowEntityNotFound() {
        // Given
        MemberChatroomDelete.Request memberChatroomDeleteRequest = MemberChatroomFixture.memberChatroomDeleteRequest(savedMember.getId(), savedChatroom1.getId());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        });
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 조회 시, 정상적으로 채팅방 목록이 조회된다.")
    void getMembers_WhenReadChatrooms_ThenReturnSuccess() {
        // Given
        // 채팅방 참가 요청
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom1.getId()));
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember.getId(), savedChatroom2.getId()));

        // When
        // 회원의 모든 채팅방을 조회
        List<MemberChatroomsRead.Response> response = memberChatroomService.getMemberChatrooms(savedMember.getId());

        // Then
        assertThat(response).isNotEmpty();
        assertThat(response).hasSize(2);

        assertThat(response)
                .extracting(MemberChatroomsRead.Response::getVotePostId)
                .containsExactlyInAnyOrder(savedChatroom1.getVotePost().getId(), savedChatroom2.getVotePost().getId());

        assertThat(response)
                .extracting(MemberChatroomsRead.Response::getTitle)
                .containsExactlyInAnyOrder(savedChatroom1.getBoardTitle(),
                        savedChatroom2.getBoardTitle());
    }
}