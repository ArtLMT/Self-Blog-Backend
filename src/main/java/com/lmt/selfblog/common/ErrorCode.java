package com.lmt.selfblog.common;

import org.springframework.http.HttpStatus;

/**
 * Centralized registry of all business error codes.
 *
 * <p>Each constant carries:
 * <ul>
 *   <li>{@code status}     — the HTTP status code to return to the client.</li>
 *   <li>{@code messageKey} — the key used to look up the i18n message in
 *       {@code i18n/messages.properties} (and locale variants).</li>
 * </ul>
 */
public enum ErrorCode {

    /* ===================== AUTH ===================== */
    INVALID_CREDENTIALS(
            HttpStatus.UNAUTHORIZED,
            "auth.invalidCredentials"
    ),

    REFRESH_TOKEN_EXPIRED(
            HttpStatus.UNAUTHORIZED,
            "auth.refreshTokenExpired"
    ),

    REFRESH_TOKEN_REVOKED(
            HttpStatus.UNAUTHORIZED,
            "auth.refreshTokenRevoked"
    ),

    REFRESH_TOKEN_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "auth.refreshTokenNotFound"
    ),

    UNAUTHENTICATED(
            HttpStatus.UNAUTHORIZED,
            "auth.unauthenticated"
    ),

    /* ===================== USER ===================== */
    USER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "user.notFound"
    ),

    USERNAME_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "user.usernameAlreadyExists"
    ),

    EMAIL_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "user.emailAlreadyExists"
    ),

    USER_NOT_ACTIVE(
            HttpStatus.FORBIDDEN,
            "user.notActive"
    ),

    /* ===================== POST ===================== */
    POST_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "post.notFound"
    ),

    /* ===================== VALIDATION ===================== */
    VALIDATION_FAILED(
            HttpStatus.BAD_REQUEST,
            "validation.failed"
    ),

    /* ===================== SECURITY ===================== */
    ACCESS_DENIED(
            HttpStatus.FORBIDDEN,
            "security.accessDenied"
    ),

    /* ===================== SYSTEM ===================== */
    INTERNAL_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "system.internalError"
    );

    private final HttpStatus status;
    private final String messageKey;

    ErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
