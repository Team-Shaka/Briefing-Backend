package com.example.briefingapi.briefing.presentation.dto;

import java.time.LocalDate;

import com.example.briefingcommon.entity.enums.BriefingType;
import com.example.briefingcommon.entity.enums.TimeOfDay;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BriefingRequestParam {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class BriefingPreviewListParam {
        @NotNull private BriefingType type;
        private LocalDate date;
        private TimeOfDay timeOfDay = TimeOfDay.MORNING;

        public boolean isPresentDate() {
            return date != null;
        }
    }
}
