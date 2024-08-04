package com.example.briefingapi.subscription.presentation;

import com.example.briefingapi.subscription.business.SubscriptionService;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.common.presentation.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "05-Subscription V2 📁", description = "구독 관련 API V2")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class SubscriptionApi {

    private final SubscriptionService subscriptionService;

    @PostMapping("/subscriptions")
    public CommonResponse<Void> createSubscription(@RequestBody SubscriptionRequest.ReceiptDTO request) {
        subscriptionService.createSubscription(request);
        return CommonResponse.onSuccess();
    }

    @GetMapping("/subscriptions/members/{memberId}")
    public CommonResponse<SubscriptionResponse.SubscriptionDTO> getSubscriptionByMemberId(@PathVariable Long memberId) {
        return CommonResponse.onSuccess(subscriptionService.getActiveSubscriptionByMemberId(memberId));
    }

}
