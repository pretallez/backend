package com.pretallez.domain.auth.service;

import com.pretallez.domain.auth.dto.KakaoOauthLogin;
import com.pretallez.domain.auth.dto.KakaoOauthToken;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    //refresh token redis 에 추가
    String addRefreshToken(String email);

    //access token
    String addAccessToken(HttpServletResponse response,String email);

    void deleteRefreshToken(String email);

    void deleteAccessToken(String email);

    KakaoOauthToken.Response getAccessToken(KakaoOauthLogin.Request kakaoOauthLogin);
}
