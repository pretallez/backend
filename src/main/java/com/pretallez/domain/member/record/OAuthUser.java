package com.pretallez.domain.member.record;

public record OAuthUser(String email, String nickname) {

    public static OAuthUser of(String email, String nickname) {
        return new OAuthUser(email, nickname);
    }

}
