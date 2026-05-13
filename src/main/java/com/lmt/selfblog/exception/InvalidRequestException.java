package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;


public class InvalidRequestException extends ApiException {

    public InvalidRequestException() {
        super(ErrorCode.VALIDATION_FAILED);
    }

    public InvalidRequestException(String devMessage) {
        super(ErrorCode.VALIDATION_FAILED, devMessage);
    }
}