package com.pretallez.infrastructure.member;

import com.pretallez.domain.auth.dto.KakaoOauthToken;

public interface OAuthClient {

    String fetchMemberDetails(String accessToken);

    KakaoOauthToken.Response fetchAccessToken(String code);
}
