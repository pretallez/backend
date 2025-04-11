package com.pretallez.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class KakaoOauthLogin {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request{
        private String code;
        private String state;
        private String error;
        private String error_description;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Response{
        private String userId;
        private String email;
    }

}
