package com.pretallez.common.repository;

import com.pretallez.model.entity.Member;
import com.pretallez.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMemberRepository implements MemberRepository {

    private final Map<Long, Member> entities = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Member save(Member member) {
        boolean exists = entities.values().stream()
                .anyMatch(existingMember -> existingMember.getEmail().equals(member.getEmail()));

        if (exists) {
            throw new DataIntegrityViolationException(String.format("Email [%s]에 해당하는 회원이 이미 존재합니다.", member.getEmail()));
        }

        long id = idGenerator.getAndIncrement();
        ReflectionTestUtils.setField(member, "id", id);
        entities.put(id, member);
        return member;
    }
}
