package com.pretallez.domain.board.repository;

import com.pretallez.common.entity.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final BoardJpaRepository boardJpaRepository;

    @Override
    public Board save(Board board) {
        return boardJpaRepository.save(board);
    }

    @Override
    public Optional<Board> findById(Long boardId) {
        return boardJpaRepository.findById(boardId);
    }

}
