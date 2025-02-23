package com.pretallez.repository;

import com.pretallez.model.entity.VotePost;

import java.util.Optional;

public interface VotePostRepository {

    VotePost save(VotePost votePost);
    VotePost findById(Long id);
}
