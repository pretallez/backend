package com.pretallez.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.error.ResErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        CustomApiResponse<Void> errorBody = CustomApiResponse.ERROR(ResErrorCode.UNAUTHORIZED);

        String json = objectMapper.writeValueAsString(errorBody);
        response.getWriter().write(json);
    }
}

