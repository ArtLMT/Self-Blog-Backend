package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;

public class ForbiddenException extends ApiException {

    public ForbiddenException() {
        super(ErrorCode.ACCESS_DENIED);
    }

    /** Use when you want to log a more descriptive developer-facing message. */
    public ForbiddenException(String devMessage) {
        super(ErrorCode.ACCESS_DENIED, devMessage);
    }
}
