package com.pretallez.controller;

import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.domain.auth.service.AuthService;
import com.pretallez.domain.member.dto.MemberCreate;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/api")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AuthService authService;
    private final JwtCookieUtil jwtCookieUtil;

    @GetMapping("/auth/callback")
    public CustomApiResponse<Void> oauthCallback(HttpServletResponse response, @RequestParam("code") String code) {
        String accessToken = authService.getAccessToken(code);
        jwtCookieUtil.addJwtCookie(response,accessToken);
        return CustomApiResponse.OK(ResSuccessCode.SUCCESS);
    }

    @PostMapping("/login")
    public CustomApiResponse<Void> login(HttpServletResponse response, MemberCreate memberCreate) {
        String email = "chzhqk98@naver.com";
        authService.addAccessToken(response, email);
        authService.addRefreshToken(email);
        return CustomApiResponse.OK(ResSuccessCode.SUCCESS);
    }

    @PostMapping("/logout")
    public CustomApiResponse<Void> logout(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) {
        authService.deleteRefreshToken(userDetails.getUsername());
        authService.deleteAccessToken(response, userDetails.getUsername());
        return CustomApiResponse.OK(ResSuccessCode.SUCCESS);
    }


}