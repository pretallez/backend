package com.pretallez.infrastructure.member.oauthclient;

import com.pretallez.application.member.dto.kakaoAccount;
import com.pretallez.domain.auth.dto.KakaoOauthToken;

public interface OAuthClient {

    kakaoAccount fetchMemberDetails(String accessToken);

    KakaoOauthToken.Response fetchAccessToken(String code);
}
