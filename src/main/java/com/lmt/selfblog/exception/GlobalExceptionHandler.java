package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;
import com.lmt.selfblog.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Centralized exception handler that converts every exception thrown in a
 * controller or service layer into a consistent {@link ApiResponse} envelope.
 *
 * <p>Priority order (most specific → least specific):
 * <ol>
 *   <li>{@link ApiException}            — all custom business errors</li>
 *   <li>{@link MethodArgumentNotValidException} — {@code @Valid} / {@code @Validated} failures</li>
 *   <li>{@link BadCredentialsException} — Spring Security auth failure</li>
 *   <li>{@link AccessDeniedException}   — Spring Security authorization failure</li>
 *   <li>{@link Exception}               — unexpected / unhandled errors (HTTP 500)</li>
 * </ol>
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    // -------------------------------------------------------------------------
    // 1. Custom Business Exceptions (ApiException hierarchy)
    // -------------------------------------------------------------------------

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(
            ApiException ex,
            HttpServletRequest request,
            Locale locale
    ) {
        log.warn("[{}] {} — path={}", ex.getCode().name(), ex.getMessage(), request.getRequestURI());

        String message = resolveMessage(ex.getCode().getMessageKey(), locale);

        return ResponseEntity
                .status(ex.getCode().getStatus())
                .body(ApiResponse.error(message, ex.getCode().name()));
    }

    // -------------------------------------------------------------------------
    // 2. Bean Validation Failures  (@Valid / @Validated on request bodies)
    // -------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            Locale locale
    ) {
        log.warn("[VALIDATION_FAILED] {} field errors — path={}", ex.getErrorCount(), request.getRequestURI());

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value",
                        // Keep the first error message when the same field fails multiple constraints
                        (existing, replacement) -> existing
                ));

        String message = resolveMessage(ErrorCode.VALIDATION_FAILED.getMessageKey(), locale);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(message, ErrorCode.VALIDATION_FAILED.name(), fieldErrors));
    }

    // -------------------------------------------------------------------------
    // 3. Spring Security — Authentication failure (wrong credentials)
    // -------------------------------------------------------------------------

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request,
            Locale locale
    ) {
        log.warn("[INVALID_CREDENTIALS] {} — path={}", ex.getMessage(), request.getRequestURI());

        String message = resolveMessage(ErrorCode.INVALID_CREDENTIALS.getMessageKey(), locale);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(message, ErrorCode.INVALID_CREDENTIALS.name()));
    }

    // -------------------------------------------------------------------------
    // 4. Spring Security — Authorization failure (insufficient privileges)
    // -------------------------------------------------------------------------

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request,
            Locale locale
    ) {
        log.warn("[ACCESS_DENIED] {} — path={}", ex.getMessage(), request.getRequestURI());

        String message = resolveMessage(ErrorCode.ACCESS_DENIED.getMessageKey(), locale);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(message, ErrorCode.ACCESS_DENIED.name()));
    }

    // -------------------------------------------------------------------------
    // 5. Fallback — anything not matched above
    // -------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception ex,
            HttpServletRequest request,
            Locale locale
    ) {
        log.error("[INTERNAL_ERROR] Unhandled exception — path={}", request.getRequestURI(), ex);

        String message = resolveMessage(ErrorCode.INTERNAL_ERROR.getMessageKey(), locale);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(message, ErrorCode.INTERNAL_ERROR.name()));
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    /**
     * Resolves an i18n message key to its locale-specific string.
     * Falls back to the key itself if no message is registered (prevents NPE).
     */
    private String resolveMessage(String messageKey, Locale locale) {
        return messageSource.getMessage(messageKey, null, messageKey, locale);
    }
}
