package com.example.briefingapi.subscription.business;

import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionMapper {

    public static Subscription toSubscription(Member member, SubscriptionRequest.ReceiptDTO request, LocalDateTime expiryDate) {
        return Subscription.builder()
                .member(member)
                .type(request.getSubscriptionType())
                .status(LocalDateTime.now().isBefore(expiryDate) ? SubscriptionStatus.ACTIVE : SubscriptionStatus.EXPIRED)
                .expiryDate(expiryDate)
                .build();
    }

    public static SubscriptionResponse.SubscriptionDTO toSubscriptionDTO(Subscription subscription) {
        return SubscriptionResponse.SubscriptionDTO.builder()
                .id(subscription.getId())
                .memberId(subscription.getMember().getId())
                .type(subscription.getType())
                .status(subscription.getStatus())
                .expiryDate(subscription.getExpiryDate())
                .build();
    }

}
