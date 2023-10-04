package briefing.feign.oauth.google.client;

import briefing.feign.oauth.google.config.GoogleOauth2FeignConfiguration;
import briefing.feign.oauth.google.dto.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleOauth2Client",
        url = "https://www.googleapis.com/oauth2/v3",
        configuration = GoogleOauth2FeignConfiguration.class
)
public interface GoogleOauth2Client {
    @GetMapping("/tokeninfo")
    GoogleUserInfo verifyToken(@RequestParam("id_token") String id_token);
}
