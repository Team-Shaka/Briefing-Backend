package com.example.briefingapi.subscription.business;

import com.example.briefingapi.subscription.presentation.dto.SubscriptionRequest;
import com.example.briefingapi.subscription.presentation.dto.SubscriptionResponse;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Subscription;
import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionMapper {

    public static Subscription toSubscription(Member member, SubscriptionRequest.ReceiptDTO request) {
        return Subscription.builder()
                .member(member)
                .type(request.getSubscriptionType())
                .status(SubscriptionStatus.ACTIVE)
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
