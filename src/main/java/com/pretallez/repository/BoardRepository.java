package com.pretallez.repository;

import com.pretallez.model.entity.Board;

import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findById(Long boardId);
}
