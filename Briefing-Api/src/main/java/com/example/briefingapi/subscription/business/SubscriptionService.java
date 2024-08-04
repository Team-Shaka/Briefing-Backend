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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.example.briefingcommon.entity.enums.SubscriptionStatus.ACTIVE;
import static com.example.briefingcommon.entity.enums.SubscriptionStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final MemberQueryAdapter memberQueryAdapter;
    private final SubscriptionCommandAdapter subscriptionCommandAdapter;
    private final SubscriptionQueryAdapter subscriptionQueryAdapter;
    private final GooglePlayFeignClient googlePlayFeignClient;

    @Value("${subscription.google.package-name}")
    private String GOOGLE_PACKAGE_NAME;

    @Transactional
    public void createSubscription(final SubscriptionRequest.ReceiptDTO request) {
        SubscriptionPurchaseResponse purchase = verifyReceipt(request);
        validateReceipt(request);

        Member member = memberQueryAdapter.findById(request.getMemberId());

        // 활성된 구독 존재 여부 확인
        boolean activeSubscriptionExists = subscriptionQueryAdapter.findByMemberId(member.getId()).stream()
                .anyMatch(subscription -> subscription.getStatus() == ACTIVE);

        if (activeSubscriptionExists) {
            throw new SubscriptionException(ErrorCode.ACTIVE_SUBSCRIPTION_EXISTS);
        }

        LocalDateTime expiryDate = LocalDateTime.ofEpochSecond(purchase.getExpiryTimeMillis() / 1000, 0, ZoneOffset.UTC);
        Subscription subscription = SubscriptionMapper.toSubscription(member, request, expiryDate);

        subscriptionCommandAdapter.create(subscription);
    }

    @Transactional
    public SubscriptionResponse.SubscriptionDTO getActiveSubscriptionByMemberId(final Long memberId) {
        Subscription subscription = subscriptionQueryAdapter.findAllByMemberId(memberId).stream()
                .filter(sub -> sub.getStatus() == ACTIVE)
                .findFirst()
                .orElseThrow(() -> new SubscriptionException(ErrorCode.SUBSCRIPTION_NOT_FOUND));

        updateSubscriptionStatus(subscription);

        return SubscriptionMapper.toSubscriptionDTO(subscription);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredSubscriptions() {
        List<Subscription> activeSubscriptions = subscriptionQueryAdapter.findAllActiveSubscriptions();
        LocalDateTime now = LocalDateTime.now();

        for (Subscription subscription : activeSubscriptions) {
            if (now.isAfter(subscription.getExpiryDate())) {
                updateSubscriptionStatus(subscription);
            }
        }
    }

    private SubscriptionPurchaseResponse verifyReceipt(SubscriptionRequest.ReceiptDTO request) {
        // TODO platform 구분해서 검증 진행 (GOOGLE, APPLE)
        return googlePlayFeignClient.verifyReceipt(
                request.getPackageName(),
                request.getProductId(),
                request.getToken());
    }

    private void validateReceipt(SubscriptionRequest.ReceiptDTO request) {
        // TODO platform 구분해서 검증 진행 (GOOGLE, APPLE)
        if (!GOOGLE_PACKAGE_NAME.equals(request.getPackageName())) {
            throw new SubscriptionException(ErrorCode.INVALID_SUBSCRIPTION);
        }
    }

    private void updateSubscriptionStatus(Subscription subscription) {
        if (LocalDateTime.now().isAfter(subscription.getExpiryDate())) {
            subscriptionCommandAdapter.updateSubscriptionStatus(subscription, EXPIRED);
        }
    }


}