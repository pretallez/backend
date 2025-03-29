package com.pretallez.domain.member.service;

import com.pretallez.common.entity.Member;

public interface MemberService {

    /** Member 엔티티가 존재하면 반환, 존재하지 않으면 예외를 던집니다. */
    Member getMember(Long memberId);

    /** 회원을 저장합니다. */
    void addMember();

    /** 회원 정보를 수정합니다. */
    void modifyMember();

    /** 회원을 삭제합니다. */
    void removeMember();

    /** 전체 회원을 조회합니다. (페이징 처리) */
    void getMembersWithPaging();

    /** 회원 닉네임을 조회합니다. */
    String getNickname(Long memberId);
}
