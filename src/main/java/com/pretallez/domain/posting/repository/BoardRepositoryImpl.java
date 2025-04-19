package com.pretallez.domain.posting.repository;

import com.pretallez.domain.posting.entity.Board;

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
