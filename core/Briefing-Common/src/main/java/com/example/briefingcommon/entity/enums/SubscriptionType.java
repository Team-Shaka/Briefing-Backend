package com.example.briefingcommon.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubscriptionType {
    MONTHLY("Monthly"),
    ANNUAL("Annual");

    private final String description;
}