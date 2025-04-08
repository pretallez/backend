package com.pretallez.domain.auth.service;

import com.pretallez.common.util.JwtTokenProvider;
import com.pretallez.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    public String addRefreshToken(String email) {
//        memberService.getMemberByEmail(email);
        var roles = List.of("ROLE_USER");
        String jwtToken = jwtTokenProvider.createToken(email,roles);
        redisTemplate.opsForHash().put("refreshToken", email, jwtToken);
        return jwtToken;
    }

    @Override
    public String addAccessToken() {
        return null;
    }
}
