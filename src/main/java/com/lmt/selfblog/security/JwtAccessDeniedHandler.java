package com.lmt.selfblog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageSource messageSource;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        Locale locale = request.getLocale();

        String message = messageSource.getMessage(
                ErrorCode.ACCESS_DENIED.getMessageKey(),
                null,
                locale
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        throw new AccessDeniedException(message);
//        ApiResponse<Void> apiResponse = ApiResponse.error(
//                message,
//                "FORBIDDEN"
//        );
//
//        response.getWriter().write(
//                objectMapper.writeValueAsString(apiResponse)
//        );
    }
}