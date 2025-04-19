package com.pretallez.domain.posting.repository;

import com.pretallez.domain.posting.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<Board,Long> {
}
