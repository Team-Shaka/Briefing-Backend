package com.example.briefingcommon.common.exception.common;


import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    HttpStatus status();

    String message();
}
