package com.pretallez.controller;

import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;

import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.domain.auth.dto.KakaoOauthLogin;
import com.pretallez.domain.auth.service.AuthService;
import com.pretallez.domain.member.dto.MemberCreate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AuthService authService;
    private final JwtCookieUtil jwtCookieUtil;

    @GetMapping("/auth/callback")
    public CustomApiResponse<String> oauthCallback(HttpServletResponse response, @RequestParam("code") String code) {
        String foo = authService.getAccessToken(code);
        System.out.println(foo);
        jwtCookieUtil.addJwtCookie(response,foo);
        return CustomApiResponse.OK(ResSuccessCode.SUCCESS,foo);
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response, MemberCreate memberCreate) {
        String email = "chzhqk98@naver.com";
        String refreshToken = authService.addRefreshToken(email);
        authService.addAccessToken(response, email);

        //todo : refresh, access token 생성하는 로직
        //todo : access token
    }

    @PostMapping("/logout")
    public void logout() {

        //todo : refresh token 지우는 로직
    }


}