package com.pretallez.domain.fencingclub.repository;

import org.springframework.stereotype.Repository;

import com.pretallez.common.entity.FencingClub;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FencingClubRepositoryImpl implements FencingClubRepository {

    private final FencingClubJpaRepository fencingClubJpaRepository;

    @Override
    public FencingClub save(FencingClub fencingClub) {
        return fencingClubJpaRepository.save(fencingClub);
    }
}
