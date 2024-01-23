package com.example.briefingcommon.common.exception;

import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class MemberException extends GeneralException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
