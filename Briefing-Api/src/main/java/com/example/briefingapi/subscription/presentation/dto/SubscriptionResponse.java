package com.example.briefingapi.subscription.presentation.dto;

import com.example.briefingcommon.entity.enums.SubscriptionStatus;
import com.example.briefingcommon.entity.enums.SubscriptionType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriptionResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubscriptionDTO {
        private Long id;
        private Long memberId;
        private SubscriptionType type;
        private SubscriptionStatus status;
        private LocalDateTime expiryDate;
    }

}
