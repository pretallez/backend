package com.pretallez.domain.member.repository;

import com.pretallez.common.entity.Member;

import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
}
