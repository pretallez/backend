package com.pretallez.controller;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.util.CookieUtil;
import com.pretallez.domain.auth.service.AuthService;
import com.pretallez.domain.member.dto.MemberCreate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AuthService authService;
    private CookieUtil cookieUtil;

    @PostMapping("/auth/callback")
    public CustomApiResponse<MemberCreate.Response> oauthCallback(@RequestParam String code) {

        return null;
    }

    @PostMapping("/login")
    public void login(HttpServletResponse response) {
        String jwttoken = authService.addRefreshToken("chzhqk98@naver.com");
        System.out.println(jwttoken);
        //todo : refresh, access token 생성하는 로직
        //todo : access token
    }

    @PostMapping("/logout")
    public void logout() {

        //todo : refresh token 지우는 로직
    }


}