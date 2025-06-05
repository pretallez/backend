package com.pretallez.common.fixture;

import org.springframework.stereotype.Component;

import com.pretallez.domain.fencingclub.entity.FencingClub;
import com.pretallez.domain.fencingclub.repository.FencingClubRepository;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.domain.posting.repository.BoardRepository;
import com.pretallez.domain.votepost.repository.VotePostRepository;

@Component
public class TestFixtureFactory {

	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final FencingClubRepository fencingClubRepository;
	private final VotePostRepository votePostRepository;

	public TestFixtureFactory(
		MemberRepository memberRepository,
		BoardRepository boardRepository,
		FencingClubRepository fencingClubRepository,
		VotePostRepository votePostRepository) {
			this.memberRepository = memberRepository;
			this.boardRepository = boardRepository;
			this.fencingClubRepository = fencingClubRepository;
			this.votePostRepository = votePostRepository;
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

	public VotePost createVotePost() {
		Member member = createMember();
		Board board = createBoard(member);
		FencingClub fencingClub = createFencingClub();
		return createVotePost(board, fencingClub);
	}
}

