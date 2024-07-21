package com.example.briefinginfra.feign.oauth.apple.config;

import org.springframework.context.annotation.Bean;

import com.example.briefinginfra.feign.decoder.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

public class AppleOauth2FeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Content-Type", "application/json;charset=UTF-8");
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
