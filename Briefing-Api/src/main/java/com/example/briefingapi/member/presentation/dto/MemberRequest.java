package com.example.briefingapi.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class MemberRequest {

    @Getter
    public static class LoginDTO {
        private String identityToken;
    }

    @Getter
    public static class ReissueDTO {
        @NotBlank private String refreshToken;
    }

    @Getter
    public static class ToggleDailyPushAlarmDTO{

        @NotNull private Integer permit;
        @NotBlank private String fcmToken;
    }

}
