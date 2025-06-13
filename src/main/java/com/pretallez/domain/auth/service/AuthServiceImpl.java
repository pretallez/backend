package com.pretallez.domain.auth.service;

import com.pretallez.application.member.dto.kakaoAccount;
import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.common.util.JwtTokenProvider;
import com.pretallez.domain.auth.dto.KakaoOauthToken;
import com.pretallez.domain.auth.entity.Role;
import com.pretallez.domain.auth.enums.MemberRole;
import com.pretallez.domain.member.entity.Member;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.member.service.MemberService;
import com.pretallez.infrastructure.member.oauthclient.OAuthClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String REFRESH_TOKEN_KEY = "refreshToken:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final OAuthClient oAuthClient;
    private final JwtCookieUtil jwtCookieUtil;

    @Override
    public String addRefreshToken(String email) {
//        memberService.getMemberByEmail(email);
        var roles = List.of("ROLE_USER");
        String jwtToken = jwtTokenProvider.createToken(email, roles);
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY + email, jwtToken, Duration.ofDays(7));
        return jwtToken;
    }

    @Override
    public String addAccessToken(HttpServletResponse response, String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        Role role;
        if (member.isEmpty()) {
            role = Role.of(MemberRole.USER);
        } else {
            role = Role.of(MemberRole.ADMIN);
        }
        String jwtToken = jwtTokenProvider.createToken(email, List.of(String.valueOf(role.getMemberRole())));
        jwtCookieUtil.addJwtCookie(response, jwtToken);
        return jwtToken;
    }

    @Override
    public void deleteRefreshToken(String email) {
        redisTemplate.delete(REFRESH_TOKEN_KEY + email);
    }

    @Override
    public void deleteAccessToken(HttpServletResponse response,String email) {
        jwtCookieUtil.deleteJwtCookie(response);
    }

    @Override
    public kakaoAccount getAccessToken(String code) {
        KakaoOauthToken.Response response = oAuthClient.fetchAccessToken(code);
        return oAuthClient.fetchMemberDetails(response.getAccessToken());

    }
}
