package com.example.briefingapi.exception;


import com.example.briefingcommon.common.exception.common.ErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorCode code) {
        super(code.name());
    }
}
