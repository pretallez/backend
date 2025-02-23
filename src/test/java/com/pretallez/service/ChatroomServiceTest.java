package com.pretallez.service;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.common.repository.*;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.VotePost;
import com.pretallez.service.impls.ChatroomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

class ChatroomServiceTest {

    private ChatroomService chatroomService;
    private VotePost savedVotePost;

    @BeforeEach
    void setUp() {
        FakeChatroomRepository chatroomRepository = new FakeChatroomRepository();
        FakeVotePostRepository votePostRepository = new FakeVotePostRepository();
        FakeBoardRepository boardRepository = new FakeBoardRepository();
        FakeFencingClubRepository fencingClubRepository = new FakeFencingClubRepository();
        FakeMemberRepository memberRepository = new FakeMemberRepository();

        chatroomService = new ChatroomServiceImpl(chatroomRepository, votePostRepository);

        Member savedMember = memberRepository.save(Fixture.member());
        Board savedBoard = boardRepository.save(Fixture.board(savedMember));
        FencingClub savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
        savedVotePost = votePostRepository.save(Fixture.votePost(savedBoard, savedFencingClub));
    }

    @Test
    @DisplayName("채팅방 생성 시 정상적으로 저장됩니다.")
    void addChatroom() {
        // Given
        ChatroomCreate.Request request = Fixture.chatroomCreateRequest(savedVotePost.getId());

        // When
        ChatroomCreate.Response response = chatroomService.addChatroom(request);

        // Then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getVotePostId()).isEqualTo(savedVotePost.getId());
    }

    @Test
    @DisplayName("같은 VotePost를 가진 ChatroomCreateRequest를 저장하면 예외가 발생합니다.")
    void addChatroom_duplication() {
        // Given
        ChatroomCreate.Request request = Fixture.chatroomCreateRequest(savedVotePost.getId());

        // When
        chatroomService.addChatroom(request);

        // Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            chatroomService.addChatroom(request);
        });
    }



}