package briefing.scrap.api;

import briefing.scrap.application.dto.ScrapResponse;
import briefing.scrap.domain.Scrap;

public class ScrapConverter {
    public static ScrapResponse.CreateDTO toCreateDTO(Scrap scrap) {
        return ScrapResponse.CreateDTO.builder()
                .scrapId(scrap.getId())
                .memberId(scrap.getMember().getId())
                .briefingId(scrap.getBriefing().getId())
                .createdAt(scrap.getCreatedAt())
                .build();
    }
}
