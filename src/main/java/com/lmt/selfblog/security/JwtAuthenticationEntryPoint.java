package com.lmt.selfblog.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageSource messageSource;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        Locale locale = request.getLocale();

        String message = messageSource.getMessage(
                ErrorCode.UNAUTHENTICATED.getMessageKey(),
                null,
                locale
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ApiResponse<Void> apiResponse = ApiResponse.error(
                message,
                ErrorCode.UNAUTHENTICATED.name()
                );

        response.getWriter().write(
                objectMapper.writeValueAsString(apiResponse)
        );
    }
}
