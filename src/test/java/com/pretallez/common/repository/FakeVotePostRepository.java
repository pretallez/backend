package com.pretallez.common.repository;

import com.pretallez.model.entity.VotePost;
import com.pretallez.repository.VotePostRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeVotePostRepository implements VotePostRepository {

    private final Map<Long, VotePost> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public VotePost save(VotePost votePost) {
        long id = idGenerator.getAndIncrement();
        ReflectionTestUtils.setField(votePost, "id", id);
        entities.put(id, votePost);
        return votePost;
    }

    @Override
    public VotePost findById(Long id) {
        return entities.get(id);
    }
}
