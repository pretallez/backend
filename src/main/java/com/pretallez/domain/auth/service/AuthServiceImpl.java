package com.pretallez.domain.auth.service;

import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.auth.entity.Role;
import com.pretallez.domain.auth.enums.MemberRole;
import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.common.util.JwtTokenProvider;
import com.pretallez.domain.auth.dto.KakaoOauthLogin;
import com.pretallez.domain.auth.dto.KakaoOauthToken;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestClient restClient = RestClient.create();

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtCookieUtil jwtCookieUtil;
    private static final String accessTokenKey = "accessToken:";
    private static final String refreshTokenKey = "refreshToken:";

    @Override
    public String addRefreshToken(String email) {
//        memberService.getMemberByEmail(email);
        var roles = List.of("ROLE_USER");
        String jwtToken = jwtTokenProvider.createToken(email,roles);
        redisTemplate.opsForValue().set(refreshTokenKey+email, jwtToken, Duration.ofDays(7));
        return jwtToken;
    }

    @Override
    public String addAccessToken(HttpServletResponse response,String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        Role role;
        if (member.isEmpty()) {
            role = Role.of(MemberRole.USER);
        } else {
            role = Role.of(MemberRole.ADMIN);
        }
        String jwtToken = jwtTokenProvider.createToken(email,List.of(String.valueOf(role.getMemberRole())));
        jwtCookieUtil.addJwtCookie(response,jwtToken);
        return jwtToken;
    }

    @Override
    public void deleteRefreshToken(String email) {

    }

    @Override
    public void deleteAccessToken(String email) {

    }

    @Override
    public KakaoOauthToken.Response getAccessToken(KakaoOauthLogin.Request kakaoOauthLoginRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "YOUR_CLIENT_ID");
        body.add("redirect_uri", "YOUR_REDIRECT_URI");
        body.add("code", kakaoOauthLoginRequest.getCode());
        // body.add("client_secret", "YOUR_CLIENT_SECRET"); // 보안 설정 ON 시 필요

        KakaoOauthToken.Response response1 = restClient.post()
                .uri("/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(body)
                .retrieve()
                .body(KakaoOauthToken.Response.class);



        KakaoOauthToken.Response response = restClient.post()
                .uri("/v2/user/me")
                .header("Authorization: Bearer "+ response1.getAccessToken())
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(body)
                .retrieve()
                .body(KakaoOauthToken.Response.class);

        return response;

    }
}
