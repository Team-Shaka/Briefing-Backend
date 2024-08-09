package com.example.briefingapi.subscription.business;

import com.example.briefingapi.config.GoogleCredentialsConfig;
import com.example.briefingapi.subscription.implement.SubscriptionCommandAdapter;
import com.example.briefingapi.subscription.implement.SubscriptionQueryAdapter;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.common.exception.SubscriptionException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Subscription;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.example.briefingcommon.entity.enums.SubscriptionStatus.ACTIVE;
import static com.example.briefingcommon.entity.enums.SubscriptionStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionCommandAdapter subscriptionCommandAdapter;
    private final SubscriptionQueryAdapter subscriptionQueryAdapter;
    private final GoogleCredentialsConfig googleCredentialsConfig;

    public SubscriptionPurchase googleInAppPurchaseVerify(String packageName, String productId, String purchaseToken) {
        try {
            AndroidPublisher publisher = googleCredentialsConfig.androidPublisher();
            AndroidPublisher.Purchases.Subscriptions.Get request = publisher.purchases().subscriptions()
                    .get(packageName, productId, purchaseToken);
            SubscriptionPurchase purchase = request.execute();

            // 결제가 완료되지 않은 경우 예외 발생
            if (purchase.getPaymentState() != null && purchase.getPaymentState() != 1) {
                throw new SubscriptionException(ErrorCode.PAYMENT_NOT_COMPLETED);
            }

            return purchase;
        } catch (GeneralSecurityException | IOException e) {
            throw new SubscriptionException(ErrorCode.INVALID_SUBSCRIPTION);
        }
    }

    @Transactional
    public void handleSubscriptionCreation(final Member member, final SubscriptionRequest.ReceiptDTO request, SubscriptionPurchase purchase) {
        boolean activeSubscriptionExists = subscriptionQueryAdapter.existsByMemberIdAndStatus(member.getId(), ACTIVE);

        if (activeSubscriptionExists) {
            throw new SubscriptionException(ErrorCode.ACTIVE_SUBSCRIPTION_EXISTS);
        }

        LocalDateTime expiryDate = LocalDateTime.ofEpochSecond(purchase.getExpiryTimeMillis() / 1000, 0, ZoneOffset.UTC);
        Subscription subscription = SubscriptionMapper.toSubscription(member, request, expiryDate);

        subscriptionCommandAdapter.create(subscription);
    }

    @Transactional
    public SubscriptionResponse.SubscriptionDTO getActiveSubscriptionByMemberId(Member member, final Long memberId) {
        validateMember(member, memberId);

        Subscription subscription = subscriptionQueryAdapter.findFirstByMemberIdAndStatusOrderByExpiryDateDesc(memberId, ACTIVE)
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

    private void updateSubscriptionStatus(Subscription subscription) {
        if (LocalDateTime.now().isAfter(subscription.getExpiryDate())) {
            subscriptionCommandAdapter.updateSubscriptionStatus(subscription, EXPIRED);
        }
    }

    private void validateMember(Member member, Long memberId) {
        if (!member.getId().equals(memberId)) {
            throw new SubscriptionException(ErrorCode.MEMBER_NOT_SAME);
        }
    }

}