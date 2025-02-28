package com.pretallez.common.repository;

import com.pretallez.model.entity.FencingClub;
import com.pretallez.repository.FencingClubRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeFencingClubRepository implements FencingClubRepository {

    private final Map<Long, FencingClub> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public FencingClub save(FencingClub fencingClub) {
        long id = idGenerator.getAndIncrement();
        ReflectionTestUtils.setField(fencingClub, "id", id);
        entities.put(id, fencingClub);
        return fencingClub;
    }
}
