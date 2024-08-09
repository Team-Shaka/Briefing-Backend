package com.example.briefingcommon.domain.repository.subscription;

import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByMemberIdOrderByExpiryDateDesc(Long memberId);

    List<Subscription> findAllByMemberId(Long memberId);

    List<Subscription> findAllByStatus(SubscriptionStatus subscriptionStatus);

    boolean existsByMemberIdAndStatus(Long memberId, SubscriptionStatus status);

    Optional<Subscription> findFirstByMemberIdAndStatusOrderByExpiryDateDesc(Long memberId, SubscriptionStatus status);

}
