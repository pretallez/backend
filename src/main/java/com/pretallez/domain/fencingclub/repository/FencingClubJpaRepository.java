package com.pretallez.domain.fencingclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pretallez.common.entity.FencingClub;

public interface FencingClubJpaRepository extends JpaRepository<FencingClub, Long> {
}
