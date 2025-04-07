package com.pretallez.controller;

import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.domain.member.dto.MemberCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/api")
@RequiredArgsConstructor
@RestController
public class MemberController {

    @PostMapping("/auth/callback")
    public CustomApiResponse<MemberCreate.Response> oauthCallback(@RequestParam String code) {

        return null;
    }

    @PostMapping("/login")
    public void login() {
        //todo : refresh, access token 생성하는 로직
        //todo : access token
    }

    @PostMapping("/logout")
    public void logout() {
        //todo : refresh token 지우는 로직
    }


}