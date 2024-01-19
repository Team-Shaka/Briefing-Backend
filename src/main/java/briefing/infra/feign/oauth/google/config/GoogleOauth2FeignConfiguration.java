package briefing.infra.feign.oauth.google.config;

import org.springframework.context.annotation.Bean;

import briefing.infra.feign.decoder.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;

public class GoogleOauth2FeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Content-Type", "application/json; charset=UTF-8");
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
