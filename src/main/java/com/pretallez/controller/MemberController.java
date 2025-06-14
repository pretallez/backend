package com.pretallez.controller;

import com.pretallez.common.enums.success.ResSuccessCode;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.util.JwtCookieUtil;
import com.pretallez.common.util.JwtTokenProvider;
import com.pretallez.domain.auth.service.AuthService;
import com.pretallez.domain.member.dto.MemberCreate;
import com.pretallez.infrastructure.member.dto.kakao.KakaoUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/api")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final AuthService authService;
    private final JwtCookieUtil jwtCookieUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/auth/callback")
    public CustomApiResponse<Void> oauthCallback(HttpServletResponse response, @RequestParam("code") String code) {
        KakaoUserResponse userResponse = authService.getAccessToken(code);
        String userEmail = userResponse.kakaoAccount().email();
        String userInfo = jwtTokenProvider.createToken(userEmail, List.of("ROLE_USER"));
        jwtCookieUtil.addJwtCookie(response, userInfo);
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

    @GetMapping("/auth/info")
    public CustomApiResponse<AuthInfoResponse> getAuthInfo(HttpServletRequest request) {
        String userToken = jwtTokenProvider.resolveToken(request);
        String username = jwtTokenProvider.getUsername(userToken);
        AuthInfoResponse authInfoResponse = new AuthInfoResponse(username, List.of("ROLE_USER"));
        return CustomApiResponse.OK(ResSuccessCode.SUCCESS, authInfoResponse);
    }

    public record AuthInfoResponse(
            String email,
            List<String> role
    ) {
    }


}