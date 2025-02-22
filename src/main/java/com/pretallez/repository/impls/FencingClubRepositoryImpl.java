package com.pretallez.repository.impls;

import com.pretallez.model.entity.FencingClub;
import com.pretallez.repository.FencingClubRepository;
import com.pretallez.repository.jpa.FencingClubJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FencingClubRepositoryImpl implements FencingClubRepository {

    private final FencingClubJpaRepository fencingClubJpaRepository;

    @Override
    public FencingClub save(FencingClub fencingClub) {
        return fencingClubJpaRepository.save(fencingClub);
    }
}
