package com.pretallez.infrastructure.member;

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
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.redirect-url}")
    private String redirectUrl;

    @Override
    public String fetchMemberDetails(String accessToken) {
        String response = restClient.post()
                .uri(USER_INFO_URL)
                .header("Authorization", BEARER_PREFIX + accessToken)
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .body(String.class);
        return response;
    }

    @Override
    public KakaoOauthToken.Response fetchAccessToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUrl);
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
