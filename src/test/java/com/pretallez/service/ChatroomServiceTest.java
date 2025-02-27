package com.pretallez.service;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.chatroom.ChatroomCreate;
import com.pretallez.model.entity.*;
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
    private MemberChatroomRepository memberChatroomRepository;

    private ChatroomService chatroomService;
    private VotePost savedVotePost;
    private Member savedMember;

    @BeforeEach
    void setUp() {
        chatroomService = new ChatroomServiceImpl(chatroomRepository, votePostRepository, memberRepository, memberChatroomRepository);

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

    @Test
    @DisplayName("회원이 채팅방에 정상적으로 참가합니다.")
    void addMemberToChatroom() {
        // Given
        Chatroom savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // When
        MemberChatroom savedMemberChatroom = memberChatroomRepository.save(Fixture.memberChatroom(savedMember, savedChatroom));

        // Given
        assertThat(savedMemberChatroom.getId()).isNotNull();
        assertThat(savedMemberChatroom.getMember()).isEqualTo(savedMember);
        assertThat(savedMemberChatroom.getChatroom()).isEqualTo(savedChatroom);
    }

    @Test
    @DisplayName("회원이 이미 참가한 채팅방에 참가 시 예외가 발생합니다.")
    void addMemberToChatroom_duplication() {
        // Given
        Chatroom savedChatroom = chatroomRepository.save(Fixture.chatroom(savedVotePost));

        // When
        memberChatroomRepository.save(Fixture.memberChatroom(savedMember, savedChatroom));

        // Then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            memberChatroomRepository.save(Fixture.memberChatroom(savedMember, savedChatroom));
        });
    }
}