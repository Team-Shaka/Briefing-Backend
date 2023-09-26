package briefing.scrap.application.dto;

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
    }
}
