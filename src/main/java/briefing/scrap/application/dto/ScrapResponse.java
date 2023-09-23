package briefing.scrap.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
