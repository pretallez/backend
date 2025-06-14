package com.pretallez.infrastructure.member.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoProfile(
        @JsonProperty("nickname") String nickname,
        @JsonProperty("thumbnail_image_url") String thumbnailImageUrl,
        @JsonProperty("profile_image_url") String profileImageUrl,
        @JsonProperty("is_default_image") boolean isDefaultImage,
        @JsonProperty("is_default_nickname") boolean isDefaultNickname
) {}