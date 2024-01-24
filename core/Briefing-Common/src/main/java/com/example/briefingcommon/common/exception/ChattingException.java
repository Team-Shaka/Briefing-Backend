package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class ChattingException extends GeneralException {
    public ChattingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
