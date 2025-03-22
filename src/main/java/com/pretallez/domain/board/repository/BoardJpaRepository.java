package com.pretallez.domain.board.repository;

import com.pretallez.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board,Long> {
}
