package com.lmt.selfblog.exception;

import com.lmt.selfblog.common.ErrorCode;

public class ConflictException extends ApiException {

  public ConflictException(ErrorCode code) {
    super(code);
  }

  public ConflictException(ErrorCode code, String devMessage) {
    super(code, devMessage);
  }
}