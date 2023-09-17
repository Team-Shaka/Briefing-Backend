package briefing.feign.oauth.apple.client;

import briefing.feign.oauth.apple.config.AppleOauth2FeignConfiguration;
import briefing.feign.oauth.apple.dto.ApplePublicKeyList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth",configuration = AppleOauth2FeignConfiguration.class)
@Component
public interface AppleOauth2Client {
    @GetMapping("/keys")
    ApplePublicKeyList getApplePublicKeys();
}
