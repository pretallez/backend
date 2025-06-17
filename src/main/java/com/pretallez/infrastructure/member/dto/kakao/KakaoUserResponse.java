package com.pretallez.infrastructure.member.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("connected_at") String connectedAt,
        @JsonProperty("kakao_properties") KakaoProperties properties,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {}
