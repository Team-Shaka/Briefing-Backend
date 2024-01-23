package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class AppleOAuthException extends GeneralException {
    public AppleOAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
