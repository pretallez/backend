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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


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
    public String getAccessToken(String code) {
        RestClient restClient = RestClient.builder()
                .build();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "c44a0c82645a80913ae47dff90012632");
        body.add("redirect_uri", "http://192.168.0.9:5173/auth");
        body.add("code", code);
        // body.add("client_secret", "YOUR_CLIENT_SECRET"); // 보안 설정 ON 시 필요

        /*
        * {"access_token":"_FP_GxZMZmWOm7bQc9LrlLpPKeXT3jZyAAAAAQoNFZsAAAGWUqEhUFv0-avl6D9k",
        * "token_type":"bearer",
        * "refresh_token":"-NHAbBWfB7LcR7sNnCrhf1CskoSfXXS5AAAAAgoNFZsAAAGWUqEhR1v0-avl6D9k",
        * "expires_in":21599,
        * "refresh_token_expires_in":5183999}
        * */
        KakaoOauthToken.Response response1 = restClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(body)
                .retrieve()
                .body(KakaoOauthToken.Response.class);

        System.out.println("Authorization: Bearer "+ response1.getAccessToken());
        log.info(response1.getAccessToken());

        String response = restClient.post()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization","Bearer " + response1.getAccessToken())
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .body(String.class);

        return response1.getAccessToken();

    }
}
