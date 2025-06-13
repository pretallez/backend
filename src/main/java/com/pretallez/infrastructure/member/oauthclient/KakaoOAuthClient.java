package com.pretallez.infrastructure.member.oauthclient;

import com.pretallez.application.member.dto.kakaoAccount;
import com.pretallez.domain.auth.dto.KakaoOauthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoOAuthClient implements OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kauth.kakao.com/userinfo";
    private static final String BEARER_PREFIX = "Bearer ";

    private final RestClient restClient = RestClient.create();

    @Value("${KAKAO_CLIENT_ID}")
    private String CLIENT_ID;
    @Value("${KAKAO_REDIRECT_URL}")
    private String REDIRECT_URL;

    @Override
    public kakaoAccount fetchMemberDetails(String accessToken) {
        kakaoAccount response = restClient.post()
                .uri(USER_INFO_URL)
                .header("Authorization", BEARER_PREFIX + accessToken)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .body(kakaoAccount.class);
        return response;
    }

    @Override
    public KakaoOauthToken.Response fetchAccessToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", CLIENT_ID);
        body.add("redirect_uri", REDIRECT_URL);
        body.add("code", code);

        KakaoOauthToken.Response response = restClient.post()
                .uri(TOKEN_URL)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(body)
                .retrieve()
                .body(KakaoOauthToken.Response.class);

        return response;
    }
}
