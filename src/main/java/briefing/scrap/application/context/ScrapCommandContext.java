package briefing.scrap.application.context;

import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.application.strategy.ScrapCommandStrategy;
import briefing.scrap.domain.Scrap;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScrapCommandContext {
    private final ScrapCommandStrategy scrapCommandStrategy;

    public Scrap create(ScrapRequest.CreateDTO request) {
        return scrapCommandStrategy.create(request);
    }

    public Scrap delete(Long briefingId, Long memberId) {
        return scrapCommandStrategy.delete(briefingId, memberId);
    }
}
