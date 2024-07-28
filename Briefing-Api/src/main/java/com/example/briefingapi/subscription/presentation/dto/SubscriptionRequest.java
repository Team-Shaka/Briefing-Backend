package com.example.briefingapi.subscription.presentation.dto;

import com.example.briefingcommon.entity.enums.SubscriptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionRequest {

    @Getter
    public static class ReceiptDTO {
        private Long memberId;
        private String packageName;
        private String subscriptionId;
        private String token;
        private SubscriptionType subscriptionType;
    }

}
