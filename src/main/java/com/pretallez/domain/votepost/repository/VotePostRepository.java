package com.pretallez.domain.votepost.repository;

import com.pretallez.domain.posting.entity.VotePost;

import java.util.Optional;

public interface VotePostRepository {

    VotePost save(VotePost votePost);
    Optional<VotePost> findById(Long id);
}
