package com.example.briefinginfra.feign.oauth.apple.client;

import com.example.briefinginfra.feign.oauth.apple.config.AppleOauth2FeignConfiguration;
import com.example.briefinginfra.feign.oauth.apple.dto.ApplePublicKeyList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "apple-public-key-client",
        url = "https://appleid.apple.com/auth",
        configuration = AppleOauth2FeignConfiguration.class)
public interface AppleOauth2Client {
    @GetMapping("/keys")
    ApplePublicKeyList getApplePublicKeys();
}
