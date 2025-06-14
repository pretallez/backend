package com.pretallez.application.member;

import com.pretallez.domain.auth.dto.KakaoOauthToken;
import com.pretallez.infrastructure.member.dto.kakao.KakaoUserResponse;

public interface OAuthClient {

    KakaoUserResponse fetchMemberDetails(String accessToken);

    KakaoOauthToken.Response fetchAccessToken(String code);
}
