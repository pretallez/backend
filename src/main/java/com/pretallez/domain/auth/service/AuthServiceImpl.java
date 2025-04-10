package com.pretallez.domain.auth.service;

import com.pretallez.common.entity.Member;
import com.pretallez.common.entity.Role;
import com.pretallez.common.enums.MemberRole;
import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.common.util.JwtTokenProvider;
import com.pretallez.domain.member.repository.MemberRepository;
import com.pretallez.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
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
}
