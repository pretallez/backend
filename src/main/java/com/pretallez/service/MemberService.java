package com.pretallez.service;

import com.pretallez.model.entity.Member;

public interface MemberService {

    /**
     * Member 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다.
     * @param memberId Member ID
     * @return Member 엔티티
     */
    Member getMemberOrThrow(Long memberId);
}
