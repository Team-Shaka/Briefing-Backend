package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class ScrapException extends GeneralException {
    public ScrapException(ErrorCode errorCode) {
        super(errorCode);
    }
}
