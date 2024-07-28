package com.example.briefingcommon.common.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.common.exception.common.GeneralException;

public class SubscriptionException extends GeneralException {
    public SubscriptionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
