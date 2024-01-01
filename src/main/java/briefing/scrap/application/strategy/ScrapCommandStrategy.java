package briefing.scrap.application.strategy;

import briefing.common.enums.APIVersion;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;

public interface ScrapCommandStrategy {
    Scrap create(ScrapRequest.CreateDTO request);

    Scrap delete(Long briefingId, Long memberId);

    APIVersion getVersion();
}
