package com.pretallez.service;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.*;
import com.pretallez.service.impls.ChatroomServiceImpl;
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
class ChatroomServiceTest {

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
    private VotePostService votePostService;

    private ChatroomService chatroomService;
    private VotePost savedVotePost;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        chatroomService = new ChatroomServiceImpl(votePostService, chatroomRepository);

        savedMember = memberRepository.save(Fixture.member());
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