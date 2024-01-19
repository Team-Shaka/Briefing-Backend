package briefing.briefing.presentation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import briefing.common.enums.BriefingType;
import briefing.common.enums.TimeOfDay;
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
