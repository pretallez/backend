package com.pretallez.domain.auth.service;

public interface AuthService {

    //refresh token redis 에 추가
    String addRefreshToken(String email);

    //access token
    String addAccessToken();
}
