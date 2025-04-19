package com.pretallez.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.pretallez.domain.posting.entity.Board;
import com.pretallez.domain.fencingclub.entity.FencingClub;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.common.fixture.TestFixtureFactory;
import com.pretallez.common.fixture.VotePostFixture;
import com.pretallez.domain.posting.dto.votepost.VotePostCreate;
import com.pretallez.domain.votepost.repository.VotePostRepository;
import com.pretallez.domain.votepost.service.VotePostService;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
@DisplayName("대관 게시글 서비스 통합 테스트")
public class VotePostServiceImplIntegrationTest {

    @Autowired
    private VotePostService votePostService;

    @Autowired
    private VotePostRepository votePostRepository;

    @Autowired
    private TestFixtureFactory testFixtureFactory;

    private Member savedMember;
    private FencingClub savedFencingClub;

    @BeforeEach
    void setUp() {
        savedMember = testFixtureFactory.createMember();
        savedFencingClub = testFixtureFactory.createFencingClub();
    }

    @Test
    @DisplayName("대관 신청 참여 글 작성 시, 성공적으로 글이 생성되고 필드 값이 올바르게 저장되는지 확인")
    public void whenCreateRequestVotePost_ThenSuccess() {
        //given
        VotePostCreate.Request votePostCreateRequest = VotePostFixture.votePostCreateRequest(savedFencingClub);
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
