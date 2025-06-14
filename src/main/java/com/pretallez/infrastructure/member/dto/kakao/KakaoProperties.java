package com.pretallez.infrastructure.member.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoProperties(
        @JsonProperty("nickname") String nickname,
        @JsonProperty("profile_image") String profileImage,
        @JsonProperty("thumbnail_image") String thumbnailImage
) {}