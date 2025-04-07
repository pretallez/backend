package com.pretallez.domain.auth.service;

public interface AuthService {

    //refresh token redis 에 추가
    void addRefreshToken();

    //access token
    void addAccessToken();
}
