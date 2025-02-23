package com.pretallez.common.repository;

import com.pretallez.model.entity.Board;
import com.pretallez.repository.BoardRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeBoardRepository implements BoardRepository {

    private final Map<Long, Board> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Board save(Board board) {
        long id = idGenerator.getAndIncrement();
        ReflectionTestUtils.setField(board, "id", id);
        entities.put(id, board);
        return board;
    }
}
