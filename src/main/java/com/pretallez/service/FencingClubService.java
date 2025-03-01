package com.pretallez.service;

public interface FencingClubService {

    /** 전체 펜싱클럽을 조회합니다. (페이징 처리) */
    void getFencingClubsWithPaging();

    /** 펜싱클럽 상세정보를 조회합니다. */
    void getFencingClubDetails();
}
