package briefing.scrap.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import briefing.briefing.domain.TimeOfDay;
import briefing.common.enums.GptModel;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDTO {
        private Long scrapId;
        private Long memberId;
        private Long briefingId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDTOV2 {
        private Long scrapId;
        private Long memberId;
        private Long briefingId;
        private Boolean isScrap;
        private Integer scrapCount;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteDTO {
        private Long scrapId;
        private LocalDateTime deletedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteDTOV2 {
        private Long scrapId;
        private Boolean isScrap;
        private Integer scrapCount;
        private LocalDateTime deletedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadDTO {
        private Long briefingId;
        private Integer ranks;
        private String title;
        private String subtitle;
        private LocalDate date;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadDTOV2 {
        private Long briefingId;
        private Integer ranks;
        private String title;
        private String subtitle;
        private LocalDate date;
        @Builder.Default private GptModel gptModel = GptModel.GPT_3_5_TURBO;
        @Builder.Default private TimeOfDay timeOfDay = TimeOfDay.MORNING;
    }
}
