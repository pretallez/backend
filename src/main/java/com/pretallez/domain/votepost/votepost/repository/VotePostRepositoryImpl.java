package com.pretallez.domain.votepost.votepost.repository;

import com.pretallez.common.entity.VotePost;
import com.pretallez.votepost.repository.VotePostJpaRepository;
import com.pretallez.votepost.repository.VotePostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
