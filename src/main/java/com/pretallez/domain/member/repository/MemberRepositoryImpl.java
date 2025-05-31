package com.pretallez.domain.member.repository;

import static com.pretallez.domain.member.entity.QMember.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.pretallez.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Map<Long, String> findNicknamesByIds(Set<Long> ids) {
        return queryFactory
            .select(member.id, member.nickname)
            .from(member)
            .where(member.id.in(ids))
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                row -> row.get(member.id),
                row -> row.get(member.nickname)
            ));
    }
}
