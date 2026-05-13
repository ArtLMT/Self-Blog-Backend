package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;

public class NotFoundException extends ApiException {

    public NotFoundException(ErrorCode code) {
        super(code);
    }

    public NotFoundException(ErrorCode code, String devMessage) {
        super(code, devMessage);
    }
}
