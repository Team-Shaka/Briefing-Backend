package com.example.briefingapi.subscription.business;

import com.example.briefingapi.member.implement.MemberQueryAdapter;
import com.example.briefingapi.subscription.implement.SubscriptionCommandAdapter;
import com.example.briefingapi.subscription.implement.SubscriptionQueryAdapter;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.common.exception.SubscriptionException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefinginfra.feign.subscription.client.GooglePlayFeignClient;
import com.example.briefinginfra.feign.subscription.dto.SubscriptionPurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.example.briefingcommon.entity.enums.SubscriptionStatus.ACTIVE;
import static com.example.briefingcommon.entity.enums.SubscriptionStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final MemberQueryAdapter memberQueryAdapter;
    private final SubscriptionCommandAdapter subscriptionCommandAdapter;
    private final SubscriptionQueryAdapter subscriptionQueryAdapter;
    private final GooglePlayFeignClient googlePlayFeignClient;

    @Transactional
    public void createSubscription(final SubscriptionRequest.ReceiptDTO request) {
//        if (subscriptionQueryAdapter.existsByMemberIdAndSubscriptionType(
//                request.getMemberId(), request.getSubscriptionType())) {
//            throw new SubscriptionException(ErrorCode.SUBSCRIPTION_ALREADY_EXISTS);
//        }

        SubscriptionPurchaseResponse purchase = googlePlayFeignClient.verifyReceipt(
                request.getPackageName(),
                request.getSubscriptionId(),
                request.getToken());
        Member member = memberQueryAdapter.findById(request.getMemberId());
        Subscription subscription = SubscriptionMapper.toSubscription(member, request);
        subscription.setExpiryDate(LocalDateTime.ofEpochSecond(purchase.getExpiryTimeMillis() / 1000, 0, ZoneOffset.UTC));
        subscription.setStatus(purchase.getPaymentState() == 1 ? ACTIVE : EXPIRED);

        subscriptionCommandAdapter.create(subscription);
    }

    @Transactional(readOnly = true)
    public SubscriptionResponse.SubscriptionDTO getSubscriptionByMemberId(final Long memberId) {
        Subscription subscription = subscriptionQueryAdapter.findByMemberId(memberId)
                .orElseThrow(() -> new SubscriptionException(ErrorCode.SUBSCRIPTION_NOT_FOUND));
        return SubscriptionMapper.toSubscriptionDTO(subscription);
    }

}