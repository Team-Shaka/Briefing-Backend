package briefing.scrap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapRequest {

    @Getter
    public static class CreateDTO {
        private Long memberId;
        private Long briefingId;
    }
}
