package com.example.briefingapi.fcm.presentation.dto;

import lombok.Getter;

public class FcmRequest {

    @Getter
    public static class FcmTokenDTO{
        String token;
    }
}
