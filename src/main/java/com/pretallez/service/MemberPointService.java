package com.pretallez.service;

public interface MemberPointService {

    /** 특정 회원의 포인트 내역을 조회합니다. */
    void getMemberPointsWithPaging();

    /** 회원의 포인트를 충전합니다. */
    void incrementMemberPoints();

    /** 회원의 포인트를 환급합니다. */
    void decrementMemberPoints();
}
