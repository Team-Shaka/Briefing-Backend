package briefing.scrap.application.strategy;

import briefing.common.enums.APIVersion;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;

public interface ScrapCommandStrategy {
    Scrap create(ScrapRequest.CreateDTO request, APIVersion version);
    Scrap delete(Long briefingId, Long memberId, APIVersion version);
    APIVersion getVersion();
}
