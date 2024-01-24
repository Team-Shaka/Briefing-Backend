package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class BriefingException extends GeneralException {
    public BriefingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
