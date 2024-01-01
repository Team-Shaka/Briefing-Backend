package briefing.scrap.application.dto;

import java.time.LocalDateTime;

import briefing.scrap.domain.Scrap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScrapV2 extends Scrap {
    private Integer scrapCount;
    private Boolean isScrap;
    private LocalDateTime createdAt;

    private ScrapV2(Scrap scrap, Integer scrapCount, Boolean isScrap) {
        super(scrap.getId(), scrap.getMember(), scrap.getBriefing());
        this.scrapCount = scrapCount;
        this.isScrap = isScrap;
        this.createdAt = scrap.getCreatedAt();
    }

    public static ScrapV2 of(Scrap scrap, Integer scrapCount, Boolean isScrap) {
        return new ScrapV2(scrap, scrapCount, isScrap);
    }
}
