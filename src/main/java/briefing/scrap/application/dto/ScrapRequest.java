package briefing.scrap.application.dto;

import lombok.Getter;

public class ScrapRequest {

    @Getter
    public static class CreateDTO {
        private Long memberId;
        private Long briefingId;
    }
}
