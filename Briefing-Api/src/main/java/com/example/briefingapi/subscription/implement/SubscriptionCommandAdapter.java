package com.example.briefingapi.subscription.implement;

import com.example.briefingapi.annotation.Adapter;
import com.example.briefingcommon.domain.repository.subscription.SubscriptionRepository;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class SubscriptionCommandAdapter {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription create(final Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void updateSubscriptionStatus(Subscription subscription, final SubscriptionStatus status) {
        subscription.updateSubscriptionStatus(status);
    }
}
