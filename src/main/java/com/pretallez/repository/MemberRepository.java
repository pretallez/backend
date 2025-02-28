package com.pretallez.repository;

import com.pretallez.model.entity.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
}
