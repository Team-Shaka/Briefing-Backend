package com.example.briefingcommon.domain.repository.subscription;

import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findFirstByMemberIdOrderByExpiryDateDesc(Long memberId);

    boolean existsByMemberIdAndType(Long memberId, SubscriptionType type);

    Subscription findByMemberIdAndType(Long memberId, SubscriptionType type);

}
