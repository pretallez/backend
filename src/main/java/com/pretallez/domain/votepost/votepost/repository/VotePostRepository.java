package com.pretallez.domain.votepost.votepost.repository;

import com.pretallez.common.entity.VotePost;

import java.util.Optional;

public interface VotePostRepository {

    VotePost save(VotePost votePost);
    Optional<VotePost> findById(Long id);
}
