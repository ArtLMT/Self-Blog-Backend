package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;
import lombok.Getter;

/**
 *  Base class for API Exceptions
 *
 */
@Getter
public abstract class ApiException extends RuntimeException {

    private final ErrorCode code;

    private final String devMessage;

    protected ApiException(ErrorCode code) {
        super(code.name());
        this.code = code;
        this.devMessage = null;
    }

    protected ApiException(ErrorCode code, String devMessage) {
        super(devMessage);
        this.code = code;
        this.devMessage = devMessage;
    }
}
