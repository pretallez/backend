package com.pretallez.integration.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.common.exception.EntityException;
import com.pretallez.common.fixture.MemberChatroomFixture;
import com.pretallez.common.fixture.TestFixtureFactory;
import com.pretallez.domain.chatting.dto.memberchatroom.ChatroomMembersRead;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomCreate;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomDelete;
import com.pretallez.domain.chatting.dto.memberchatroom.MemberChatroomsRead;
import com.pretallez.domain.chatting.repository.memberchatroom.MemberChatroomRepository;
import com.pretallez.domain.chatting.service.memberchatroom.MemberChatroomService;

@SpringBootTest
@ActiveProfiles("local")
@DisplayName("회원 채팅방 서비스 통합 테스트")
class MemberChatroomServiceIntegrationTest {

    @Autowired
    private MemberChatroomRepository memberChatroomRepository;

    @Autowired
    private MemberChatroomService memberChatroomService;

    @Autowired
    private TestFixtureFactory testFixtureFactory;

    private Member savedMember1;
    private Member savedMember2;
    private Chatroom savedChatroom1;
    private Chatroom savedChatroom2;

    @BeforeEach
    void setUp() {
        savedMember1 = testFixtureFactory.createMember();
        savedChatroom1 = testFixtureFactory.createChatroomWithMember(savedMember1);

        savedMember2 = testFixtureFactory.createMember();
        savedChatroom2 = testFixtureFactory.createChatroomWithMember(savedMember2);
    }

    @Test
    @DisplayName("채팅방 참가 시, 성공")
    void addMemberToChatroom_WhenMemberChatroomNotExists_ThenMemberChatroomIsCreated() {
        // Given
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom1.getId());

        // When
        MemberChatroomCreate.Response memberChatroomCreateResponse = memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        // Given
        assertThat(memberChatroomCreateResponse.getId()).isNotNull();
        assertThat(memberChatroomCreateResponse.getMemberId()).isEqualTo(savedMember1.getId());
        assertThat(memberChatroomCreateResponse.getChatroomId()).isEqualTo(savedChatroom1.getId());
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 참가 시, 참가가 실패되고 DataIntegrityViolation 예외가 발생한다.")
    void addMemberToChatroom_WhenMemberChatroomExists_ThenThrowDataintegrityViolation() {
        // When
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom1.getId());
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
        MemberChatroomCreate.Request memberChatroomCreateRequest = MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom1.getId());
        memberChatroomService.addMemberToChatroom(memberChatroomCreateRequest);

        MemberChatroomDelete.Request memberChatroomDeleteRequest = MemberChatroomFixture.memberChatroomDeleteRequest(savedMember1.getId(), savedChatroom1.getId());

        // When
        memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);

        // Then
        Optional<MemberChatroom> existingMemberChatroom = memberChatroomRepository.findByMemberAndChatroom(savedMember1, savedChatroom1);
        assertThat(existingMemberChatroom).isEmpty();
    }

    @Test
    @DisplayName("참가되어 있지않은 채팅방 퇴장 시, 퇴장이 실패되고 EntityNotFound 예외가 발생한다.")
    void removeMemberFromChatroom_WhenMemberChatroomNotExists_ThenThrowEntityNotFound() {
        // Given
        MemberChatroomDelete.Request memberChatroomDeleteRequest = MemberChatroomFixture.memberChatroomDeleteRequest(savedMember1.getId(), savedChatroom1.getId());

        // When & Then
        assertThrows(EntityException.class, () -> {
            memberChatroomService.removeMemberFromChatroom(memberChatroomDeleteRequest);
        });
    }

    @Test
    @DisplayName("참가되어 있는 채팅방 조회 시, 정상적으로 채팅방 목록이 조회된다.")
    void getMembers_WhenReadChatrooms_ThenReturnSuccess() {
        // Given
        // 채팅방 참가 요청
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom1.getId()));
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom2.getId()));

        // When
        // 회원의 모든 채팅방을 조회
        List<MemberChatroomsRead.Response> response = memberChatroomService.getMemberChatrooms(savedMember1.getId());

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

    @Test
    @DisplayName("채팅방의 모든 회원 조회 시, 정상적으로 해당 채팅방의 회원만 조회")
    void getMembers_WhenReadMemberChatrooms_ThenReturnSuccess() {
        // Given
        // 두 명의 회원이 동일한 채팅방에 입장
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember1.getId(), savedChatroom1.getId()));
        memberChatroomService.addMemberToChatroom(MemberChatroomFixture.memberChatroomCreateRequest(savedMember2.getId(), savedChatroom1.getId()));

        // When
        // 회원의 모든 채팅방을 조회
        List<ChatroomMembersRead.Response> chatroomMembers = memberChatroomService.getChatroomMembers(savedChatroom1.getId());

        // Then
        assertThat(chatroomMembers).isNotEmpty();
        assertThat(chatroomMembers).hasSize(2);

        assertThat(chatroomMembers)
                .extracting(ChatroomMembersRead.Response::getId)
                .containsExactlyInAnyOrder(savedMember1.getId(), savedMember2.getId());

        assertThat(chatroomMembers)
                .extracting(ChatroomMembersRead.Response::getNickname)
                .containsExactlyInAnyOrder(savedMember1.getNickname(), savedMember2.getNickname());
    }
}