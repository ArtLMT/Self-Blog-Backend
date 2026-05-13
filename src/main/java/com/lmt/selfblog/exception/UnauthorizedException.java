package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(ErrorCode code) {
        super(code);
    }

    public UnauthorizedException(ErrorCode code, String devMessage) {
        super(code, devMessage);
    }
}
