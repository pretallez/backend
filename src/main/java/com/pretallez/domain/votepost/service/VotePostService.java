package com.pretallez.domain.votepost.service;

import com.pretallez.domain.posting.entity.VotePost;
import com.pretallez.domain.posting.dto.votepost.VotePostCreate;

public interface VotePostService {

    /** VotePost 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    VotePost getVotePost(Long votePostId);

    /** 투표글을 생성합니다. */
    VotePostCreate.Response addVotePost(Long writerId, VotePostCreate.Request votePostCreateRequest);

    /** 투표글을 수정합니다. */
    void modifyVotePost();

    /** 투표글을 삭제합니다. */
    void removeVotePost();

    /** 전체 투표글을 조회합니다. (페이징 처리) */
    void getVotePostsWithPaging();

    /** 특정 투표글 상세정보를 조회합니다. */
    void getVotePostDetails();
}
