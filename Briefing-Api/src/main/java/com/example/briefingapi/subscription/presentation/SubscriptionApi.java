package com.example.briefingapi.subscription.presentation;

import com.example.briefingapi.security.handler.annotation.AuthMember;
import com.example.briefingapi.subscription.business.SubscriptionService;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.common.presentation.response.CommonResponse;
import com.example.briefingcommon.entity.Member;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "06-Subscription V2 💳", description = "구독 관련 API V2")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class SubscriptionApi {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "06-01 Subscription 💳 영수증 검증 및 구독하기 V2", description = "결제 영수증을 검증하고, 구독 정보를 갱신하는 API입니다.")
    @PostMapping("/subscriptions")
    public CommonResponse<Void> createSubscription(@AuthMember Member member, @RequestBody SubscriptionRequest.ReceiptDTO request) {
        SubscriptionPurchase purchase = subscriptionService.googleInAppPurchaseVerify(request.getPackageName(), request.getProductId(), request.getPurchaseToken());
        subscriptionService.handleSubscriptionCreation(member, request, purchase);
        return CommonResponse.onSuccess();
    }

    @Operation(summary = "06-02 Subscription 💳 구독 정보 조회하기 V2", description = "구독 정보를 조회하는 API입니다. 만료된 구독 정보는 제외합니다.")
    @GetMapping("/subscriptions/members/{memberId}")
    public CommonResponse<SubscriptionResponse.SubscriptionDTO> getSubscriptionByMemberId(@AuthMember Member member, @PathVariable("memberId") Long memberId) {
        return CommonResponse.onSuccess(subscriptionService.getActiveSubscriptionByMemberId(member, memberId));
    }

}
