package com.example.briefingcommon.common.exception;

import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class RefreshTokenException extends GeneralException {
    public RefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
