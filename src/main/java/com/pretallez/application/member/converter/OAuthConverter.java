package com.pretallez.application.member.converter;

import com.pretallez.domain.member.record.OAuthUser;
import com.pretallez.infrastructure.member.dto.kakao.KakaoUserResponse;
import org.springframework.stereotype.Component;

@Component
public class OAuthConverter {

    public OAuthUser convertToOAuthUser(KakaoUserResponse kakaoUserResponse) {
        String email = kakaoUserResponse.kakaoAccount().email();
        String nickname = kakaoUserResponse.properties().nickname();
        return OAuthUser.of(email, nickname);
    }

}
