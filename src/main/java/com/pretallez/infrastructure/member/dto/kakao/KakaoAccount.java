package com.pretallez.infrastructure.member.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoAccount(
        @JsonProperty("profile_nickname_needs_agreement") boolean profileNicknameNeedsAgreement,
        @JsonProperty("profile_image_needs_agreement") boolean profileImageNeedsAgreement,
        @JsonProperty("profile") KakaoProfile profile,
        @JsonProperty("has_email") boolean hasEmail,
        @JsonProperty("email_needs_agreement") boolean emailNeedsAgreement,
        @JsonProperty("is_email_valid") boolean isEmailValid,
        @JsonProperty("is_email_verified") boolean isEmailVerified,
        @JsonProperty("email") String email
) {}