package com.pretallez.repository.impls;

import com.pretallez.common.exception.CustomApiException;
import com.pretallez.common.response.ResErrorCode;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.VotePostRepository;
import com.pretallez.repository.jpa.VotePostJpaRepository;
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
