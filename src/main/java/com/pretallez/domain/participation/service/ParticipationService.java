package com.pretallez.domain.participation.service;

public interface ParticipationService {

    /** 특정 회원이 참여한 투표를 조회합니다. (페이징 처리) */
    void getParticipationsWithPaging();

    /** 투표에 참여한 회원을 조회합니다. */
    void getMembers();

    /** 회원이 투표에 참여합니다. */
    void addParticipation();

    /** 회원이 투표를 취소합니다. */
    void removeParticiation();
}
