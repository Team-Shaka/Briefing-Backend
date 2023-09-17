package briefing.feign.appleSocial.client;

import briefing.feign.appleSocial.config.AppleOauth2FeignConfiguration;
import briefing.feign.appleSocial.dto.ApplePublicKeyList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-public-key-client", url = "https://appleid.apple.com/auth",configuration = AppleOauth2FeignConfiguration.class)
@Component
public interface AppleOauth2Client {
    @GetMapping("/keys")
    ApplePublicKeyList getApplePublicKeys();
}
