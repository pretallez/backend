package com.pretallez.infrastructure.member;

import com.pretallez.domain.auth.dto.KakaoOauthToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoOAuthClient implements OAuthClient {

    private final RestClient restClient = RestClient.create();

    @Override
    public String fetchMemberDetails(String accessToken) {
        String response = restClient.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization","Bearer " + accessToken)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .body(String.class);
        return response;
    }

    @Override
    public KakaoOauthToken.Response fetchAccessToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "c44a0c82645a80913ae47dff90012632");
        body.add("redirect_uri", "http://192.168.0.9:5173/auth");
        body.add("code", code);

        KakaoOauthToken.Response response1 = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(body)
                .retrieve()
                .body(KakaoOauthToken.Response.class);

        return response1;
    }
}
