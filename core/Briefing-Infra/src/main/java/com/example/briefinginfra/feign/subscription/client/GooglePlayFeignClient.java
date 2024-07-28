package com.example.briefinginfra.feign.subscription.client;

import com.example.briefinginfra.feign.subscription.dto.SubscriptionPurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googlePlayFeignClient", url = "https://www.googleapis.com/androidpublisher/v3/applications")
public interface GooglePlayFeignClient {

    @GetMapping("/packages/{packageName}/purchases/subscriptions/{subscriptionId}/tokens/{token}")
    SubscriptionPurchaseResponse verifyReceipt(
            @RequestParam("packageName") String packageName,
            @RequestParam("subscriptionId") String subscriptionId,
            @RequestParam("token") String token);
}
