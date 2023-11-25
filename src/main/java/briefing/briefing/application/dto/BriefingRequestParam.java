package briefing.briefing.application.dto;

import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;
import briefing.common.enums.APIVersion;
import jakarta.annotation.Nullable;
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
        @NotNull
        private LocalDate date;
        private TimeOfDay timeOfDay = TimeOfDay.MORNING;
        private APIVersion version = APIVersion.V1_1_0;
    }
}
