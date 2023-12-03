package briefing.briefing.application.dto;

import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BriefingRequestParam {

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BriefingPreviewListParam {
        @NotNull
        private BriefingType type;
        private LocalDate date;
        private TimeOfDay timeOfDay = TimeOfDay.MORNING;

        public boolean isPresentDate() {
            return date != null;
        }
    }
}
