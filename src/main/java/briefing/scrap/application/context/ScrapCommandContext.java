package briefing.scrap.application.context;

import briefing.common.enums.APIVersion;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.application.strategy.ScrapCommandStrategy;
import briefing.scrap.domain.Scrap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScrapCommandContext {
    private final ScrapCommandStrategy scrapCommandStrategy;

    public Scrap create(ScrapRequest.CreateDTO request, APIVersion version) {
        return scrapCommandStrategy.create(request, version);
    }

    public Scrap delete(Long briefingId, Long memberId, APIVersion version) {
        return scrapCommandStrategy.delete(briefingId, memberId, version);
    }
}
