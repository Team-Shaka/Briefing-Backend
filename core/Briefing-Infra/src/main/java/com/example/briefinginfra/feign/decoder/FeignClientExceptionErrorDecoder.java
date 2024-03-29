package com.example.briefinginfra.feign.decoder;

import com.example.briefingcommon.common.exception.CustomFeignClientException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientExceptionErrorDecoder implements ErrorDecoder {

    Logger logger = LoggerFactory.getLogger(FeignClientExceptionErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() <= 499) {
            logger.error("feign 400번대 에러 발생 : {}", response.reason());
            return switch (response.status()) {
                case 401 -> new CustomFeignClientException(ErrorCode.FEIGN_BAD_REQUEST);
                case 402 -> new CustomFeignClientException(ErrorCode.FEIGN_UNAUTHORIZED);
                case 403 -> new CustomFeignClientException(ErrorCode.FEIGN_FORBIDDEN);
                case 404 -> new CustomFeignClientException(ErrorCode.FEIGN_EXPIRED_TOKEN);
                case 405 -> new CustomFeignClientException(ErrorCode.FEIGN_NOT_FOUND);
                case 406 -> new CustomFeignClientException(ErrorCode.FEIGN_INTERNAL_SERVER_ERROR);
                case 407 -> new CustomFeignClientException(ErrorCode.FEIGN_METHOD_NOT_ALLOWED);
                default -> throw new IllegalStateException(
                        "Unexpected value: " + response.status());
            };
        } else {
            logger.error("feign client  500번대 에러 발생 : {}", response.reason());
            return new CustomFeignClientException(ErrorCode.FEIGN_SERVER_ERROR);
        }
    }
}
