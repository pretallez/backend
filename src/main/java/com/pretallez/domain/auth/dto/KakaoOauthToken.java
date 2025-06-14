package com.pretallez.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("scope")
        private String scope;
    }
}
