package com.pretallez.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoOauthToken {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request{
        private String grantType;
        private String clientId;
        private String redirectUri;
        private String code;
        private String clientSecret;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Response{
        private String tokenType;
        private String accessToken;
        private String idToken;
        private Integer expiresIn;
        private String refreshToken;
        private Integer refreshTokenExpiresIn;
        private String scope;
    }
}
