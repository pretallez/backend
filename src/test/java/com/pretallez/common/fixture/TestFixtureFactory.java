package com.pretallez.common.fixture;

import org.springframework.stereotype.Component;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.chatting.entity.Chatroom;
import com.pretallez.domain.fencingclub.entity.FencingClub;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.chatting.entity.MemberChatroom;
import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.domain.posting.repository.BoardRepository;
import com.pretallez.domain.chatting.repository.chatroom.ChatroomRepository;
import com.pretallez.domain.fencingclub.repository.FencingClubRepository;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.chatting.repository.memberchatroom.MemberChatroomRepository;
import com.pretallez.domain.votepost.repository.VotePostRepository;

@Component
public class TestFixtureFactory {

	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final FencingClubRepository fencingClubRepository;
	private final VotePostRepository votePostRepository;
	private final ChatroomRepository chatroomRepository;
	private final MemberChatroomRepository memberChatroomRepository;

	public TestFixtureFactory(
		MemberRepository memberRepository,
		BoardRepository boardRepository,
		FencingClubRepository fencingClubRepository,
		VotePostRepository votePostRepository,
		ChatroomRepository chatroomRepository,
		MemberChatroomRepository memberChatroomRepository) {
		this.memberRepository = memberRepository;
		this.boardRepository = boardRepository;
		this.fencingClubRepository = fencingClubRepository;
		this.votePostRepository = votePostRepository;
		this.chatroomRepository = chatroomRepository;
		this.memberChatroomRepository = memberChatroomRepository;
	}

	// 기본 엔티티 생성 메서드
	public Member createMember() {
		return memberRepository.save(MemberFixture.member());
	}

	public Board createBoard(Member member) {
		return boardRepository.save(BoardFixture.board(member));
	}

	public FencingClub createFencingClub() {
		return fencingClubRepository.save(FencingClubFixture.fencingClub());
	}

	public VotePost createVotePost(Board board, FencingClub fencingClub) {
		return votePostRepository.save(VotePostFixture.votePost(board, fencingClub));
	}

	public Chatroom createChatroom(VotePost votePost) {
		return chatroomRepository.save(ChatroomFixture.chatroom(votePost));
	}

	public MemberChatroom joinChatroom(Member member, Chatroom chatroom) {
		return memberChatroomRepository.save(MemberChatroomFixture.memberChatroom(member, chatroom));
	}

	public VotePost createVotePost() {
		Member member = createMember();
		Board board = createBoard(member);
		FencingClub fencingClub = createFencingClub();
		return createVotePost(board, fencingClub);
	}

	// 채팅방 생성 (참가 없이)
	public Chatroom createChatroom() {
		Member member = createMember();
		Board board = createBoard(member);
		FencingClub fencingClub = createFencingClub();
		VotePost votePost = createVotePost(board, fencingClub);
		return createChatroom(votePost);
	}

	// 특정 멤버가 포함된 채팅방 생성 (참가 없이)
	public Chatroom createChatroomWithMember(Member member) {
		Board board = createBoard(member);
		FencingClub fencingClub = createFencingClub();
		VotePost votePost = createVotePost(board, fencingClub);
		return createChatroom(votePost);
	}

	// 채팅방 생성 후 한 명 참가
	public MemberChatroom createAndJoinChatroom() {
		Member member = createMember();
		Chatroom chatroom = createChatroomWithMember(member);
		return joinChatroom(member, chatroom);
	}

}

