package com.example.briefinginfra.feign.subscription.dto;

import lombok.Data;

@Data
public class SubscriptionPurchaseResponse {

    private long expiryTimeMillis;
    private int paymentState;
}
