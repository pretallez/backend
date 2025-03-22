package com.pretallez.integration.service;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pretallez.common.entity.Board;
import com.pretallez.common.entity.Chatroom;
import com.pretallez.common.entity.FencingClub;
import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.VotePost;
import com.pretallez.common.fixture.BoardFixture;
import com.pretallez.common.fixture.ChatFixture;
import com.pretallez.common.fixture.ChatroomFixture;
import com.pretallez.common.fixture.FencingClubFixture;
import com.pretallez.common.fixture.MemberChatroomFixture;
import com.pretallez.common.fixture.MemberFixture;
import com.pretallez.common.fixture.VotePostFixture;
import com.pretallez.domain.board.repository.BoardRepository;
import com.pretallez.domain.chat.dto.ChatCreate;
import com.pretallez.domain.chat.repository.ChatRepository;
import com.pretallez.domain.chat.service.ChatProducer;
import com.pretallez.domain.chat.service.ChatService;
import com.pretallez.domain.chatroom.repository.ChatroomRepository;
import com.pretallez.domain.fencingclub.repository.FencingClubRepository;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.memberchatroom.repository.MemberChatroomRepository;
import com.pretallez.domain.votepost.repository.VotePostRepository;

@SpringBootTest
@DisplayName("채팅 서비스 통합 테스트")
class ChatServiceImplIntegrationTest {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatProducer chatProducer;

	@Autowired
	private ChatRepository chatRepository;

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

	@BeforeEach
	void setUp() {
		Member savedMember = memberRepository.save(MemberFixture.member());
		Board savedBoard = boardRepository.save(BoardFixture.board(savedMember));
		FencingClub savedFencingClub = fencingClubRepository.save(FencingClubFixture.fencingClub());
		VotePost savedVotePost = votePostRepository.save(VotePostFixture.votePost(savedBoard, savedFencingClub));
		Chatroom savedChatroom = chatroomRepository.save(ChatroomFixture.chatroom(savedVotePost));

		memberChatroomRepository.save(MemberChatroomFixture.memberChatroom(savedMember, savedChatroom));
	}

	@Test
	@DisplayName("채팅 저장 시, RabbitMQ를 통해 배치 처리 후 레디스에 캐싱")
	void addChat_Success() throws InterruptedException {
		// Given
		ChatCreate.Request request = ChatFixture.chatCreateReqeust();

		// When
		chatService.addChat(request);
		TimeUnit.SECONDS.sleep(6);

		// Then
		Assertions.assertFalse(chatRepository.findAll().isEmpty());
	}
}