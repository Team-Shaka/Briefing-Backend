package com.example.briefingapi.subscription.implement;

import com.example.briefingapi.annotation.Adapter;
import com.example.briefingcommon.domain.repository.subscription.SubscriptionRepository;
import com.example.briefingcommon.entity.Subscription;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class SubscriptionCommandAdapter {

    private final SubscriptionRepository subscriptionRepository;

    public Subscription create(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

}
