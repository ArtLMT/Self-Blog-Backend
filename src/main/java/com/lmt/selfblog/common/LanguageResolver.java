package com.lmt.selfblog.common;

import com.lmt.selfblog.entity.CustomUserDetails;
import com.lmt.selfblog.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class LanguageResolver {

    public Language resolveLanguage() {
        // 1. Explicit URL/Query (Highest Priority)
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            String langParam = request.getParameter("lang");
            if (langParam != null && !langParam.isBlank()) {
                return Language.fromString(langParam);
            }
        }

        // 2. User Profile (If authenticated)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                User user = ((CustomUserDetails) principal).getUser();
                if (user != null && user.getLanguagePreference() != null) {
                    return user.getLanguagePreference();
                }
            }
        }

        // 3. Accept-Language Header
        if (request != null) {
            String acceptLanguage = request.getHeader("Accept-Language");
            if (acceptLanguage != null && !acceptLanguage.isBlank()) {
                // simple parse: just take the first 2 letters (e.g. "en-US" -> "en")
                String langPrefix = acceptLanguage.split(",")[0].split("-")[0];
                return Language.fromString(langPrefix);
            }
        }

        // 4. Default Fallback
        return Language.EN;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
