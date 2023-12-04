package briefing.scrap.application.dto;

import briefing.briefing.domain.TimeOfDay;
import briefing.chatting.domain.GptModel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public static class DeleteDTO {
        private Long scrapId;
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
        @Builder.Default
        private GptModel gptModel = GptModel.GPT_3_5_TURBO;
        @Builder.Default
        private TimeOfDay timeOfDay = TimeOfDay.MORNING;
    }
}
