package com.pretallez.domain.posting.repository;

import com.pretallez.domain.posting.entity.Board;

import java.util.Optional;

public interface BoardRepository {

    Board save(Board board);

    Optional<Board> findById(Long boardId);
}
