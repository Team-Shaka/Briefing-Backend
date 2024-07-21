package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class CustomFeignClientException extends GeneralException {

    public CustomFeignClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
