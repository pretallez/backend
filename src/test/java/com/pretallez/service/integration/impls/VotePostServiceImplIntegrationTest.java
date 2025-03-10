package com.pretallez.service.integration.impls;

import com.pretallez.common.fixture.Fixture;
import com.pretallez.model.dto.VotePostCreate;
import com.pretallez.model.dto.board.BoardCreate;
import com.pretallez.model.entity.Board;
import com.pretallez.model.entity.FencingClub;
import com.pretallez.model.entity.Member;
import com.pretallez.model.entity.VotePost;
import com.pretallez.model.enums.BoardType;
import com.pretallez.repository.FencingClubRepository;
import com.pretallez.repository.MemberRepository;
import com.pretallez.repository.VotePostRepository;
import com.pretallez.service.BoardService;
import com.pretallez.service.VotePostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class VotePostServiceImplIntegrationTest {

    @Autowired
    private VotePostService votePostService;

    @Autowired
    private VotePostRepository votePostRepository;

    @Autowired
    private FencingClubRepository fencingClubRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member savedMember;
    private FencingClub savedFencingClub;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        savedMember = memberRepository.save(Fixture.member());
        savedFencingClub = fencingClubRepository.save(Fixture.fencingClub());
    }

    @Test
    @DisplayName("대관 신청 참여 글 작성 시, 성공적으로 글이 생성되고 필드 값이 올바르게 저장되는지 확인")
    public void whenCreateRequestVotePost_ThenSuccess() {
        //given
        VotePostCreate.Request votePostCreateRequest = Fixture.votePostCreateRequest(savedFencingClub);
        Long writerId = savedMember.getId();

        //when
        VotePostCreate.Response votePostCreateResponse = votePostService.addVotePost(writerId, votePostCreateRequest);

        //then
        Assertions.assertThat(votePostCreateResponse.getVotePostId()).isNotNull(); // ID가 생성되었는지 확인

        VotePost savedVotePost = votePostRepository.findById(votePostCreateResponse.getVotePostId()).orElseThrow();
        Board savedBoard = savedVotePost.getBoard();
        Assertions.assertThat(savedVotePost.getBoard()).isEqualTo(savedBoard);

        Assertions.assertThat(savedVotePost.getMaxCapacity()).isEqualTo(votePostCreateRequest.getMaxCapacity());
        Assertions.assertThat(savedVotePost.getMinCapacity()).isEqualTo(votePostCreateRequest.getMinCapacity());
        Assertions.assertThat(savedVotePost.getTotalAmount()).isEqualTo(votePostCreateRequest.getTotalAmount());
        Assertions.assertThat(savedVotePost.getFencingClub()).isEqualTo(votePostCreateRequest.getFencingClub());
        Assertions.assertThat(savedVotePost.getTrainingDate()).isEqualTo(votePostCreateRequest.getTrainingDate());

    }
}
