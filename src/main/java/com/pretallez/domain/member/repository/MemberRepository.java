package com.pretallez.domain.member.repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.pretallez.domain.member.entity.Member;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String username);
    Map<Long, String> findNicknamesByIds(Set<Long> ids);
}
