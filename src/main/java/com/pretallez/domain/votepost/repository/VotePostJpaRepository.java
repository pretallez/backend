package com.pretallez.domain.votepost.repository;

import com.pretallez.common.entity.VotePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotePostJpaRepository extends JpaRepository<VotePost, Long> {
}
