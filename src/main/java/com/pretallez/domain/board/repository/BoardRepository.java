package com.pretallez.domain.board.repository;

import com.pretallez.common.entity.Board;

import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findById(Long boardId);
}
