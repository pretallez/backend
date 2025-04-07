package com.pretallez.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretallez.common.response.CustomApiResponse;
import com.pretallez.common.response.error.ResErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        CustomApiResponse<Void> body = CustomApiResponse.ERROR(ResErrorCode.FORBIDDEN);

        String json = objectMapper.writeValueAsString(body);
        response.getWriter().write(json);
    }

}
