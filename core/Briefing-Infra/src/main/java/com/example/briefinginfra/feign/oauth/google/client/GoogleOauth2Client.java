package com.example.briefinginfra.feign.oauth.google.client;

import com.example.briefinginfra.feign.oauth.google.config.GoogleOauth2FeignConfiguration;
import com.example.briefinginfra.feign.oauth.google.dto.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleOauth2Client",
        url = "https://www.googleapis.com/oauth2/v3",
        configuration = GoogleOauth2FeignConfiguration.class)
@Component
public interface GoogleOauth2Client {
    @GetMapping("/tokeninfo")
    GoogleUserInfo verifyToken(@RequestParam("id_token") String id_token);
}
