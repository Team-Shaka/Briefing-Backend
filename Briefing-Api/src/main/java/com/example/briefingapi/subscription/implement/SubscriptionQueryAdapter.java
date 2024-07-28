package com.example.briefingapi.subscription.implement;

import com.example.briefingapi.annotation.Adapter;
import com.example.briefingcommon.domain.repository.subscription.SubscriptionRepository;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionType;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class SubscriptionQueryAdapter {

    private final SubscriptionRepository subscriptionRepository;

    public Optional<Subscription> findByMemberId(Long memberId) {
        return subscriptionRepository.findFirstByMemberIdOrderByExpiryDateDesc(memberId);
    }

    public boolean existsByMemberIdAndSubscriptionType(Long memberId, SubscriptionType subscriptionType) {
        return subscriptionRepository.existsByMemberIdAndType(memberId, subscriptionType);
    }

    public Subscription findByMemberIdAndSubscriptionType(Long memberId, SubscriptionType subscriptionType) {
        return subscriptionRepository.findByMemberIdAndType(memberId, subscriptionType);
    }

}
