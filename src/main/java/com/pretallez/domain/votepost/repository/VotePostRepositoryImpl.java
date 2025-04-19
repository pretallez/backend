package com.pretallez.domain.votepost.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.posting.entity.VotePost;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VotePostRepositoryImpl implements VotePostRepository {

    private final VotePostJpaRepository votePostJpaRepository;

    @Override
    public VotePost save(VotePost votePost) {
        return votePostJpaRepository.save(votePost);
    }

    @Override
    public Optional<VotePost> findById(Long id) {
        return votePostJpaRepository.findById(id);
    }
}
