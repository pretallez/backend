package com.pretallez.service.impls;

import com.pretallez.common.exception.EntityNotFoundException;
import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.VotePostRepository;
import com.pretallez.service.VotePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VotePostServiceimpl implements VotePostService {

    private final VotePostRepository votePostRepository;

    @Override
    public VotePost getVotePostOrThrow(Long votePostId) {
        return votePostRepository.findById(votePostId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ID [%d]에 해당하는 투표 게시글을 찾을 수 없습니다.", votePostId)));
    }
}
