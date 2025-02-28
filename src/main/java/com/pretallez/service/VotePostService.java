package com.pretallez.service;

import com.pretallez.model.entity.VotePost;

public interface VotePostService {

    /**
     * VotePost 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다.
     * @param votePostId VotePost ID
     * @return VotePost 엔티티
     */
    VotePost getVotePostOrThrow(Long votePostId);
}
