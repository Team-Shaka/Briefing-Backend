package com.example.briefingapi.subscription.implement;

import com.example.briefingapi.annotation.Adapter;
import com.example.briefingcommon.domain.repository.subscription.SubscriptionRepository;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class SubscriptionQueryAdapter {

    private final SubscriptionRepository subscriptionRepository;

    public Optional<Subscription> findByMemberId(Long memberId) {
        return subscriptionRepository.findFirstByMemberIdOrderByExpiryDateDesc(memberId);
    }

    public List<Subscription> findAllByMemberId(Long memberId) {
        return subscriptionRepository.findAllByMemberId(memberId);
    }

    public List<Subscription> findAllActiveSubscriptions() {
        return subscriptionRepository.findAllByStatus(SubscriptionStatus.ACTIVE);
    }
}
