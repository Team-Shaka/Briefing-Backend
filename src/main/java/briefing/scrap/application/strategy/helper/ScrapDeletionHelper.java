package briefing.scrap.application.strategy.helper;

import org.springframework.stereotype.Component;

import briefing.exception.ErrorCode;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import briefing.scrap.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapDeletionHelper {
    private final ScrapRepository scrapRepository;

    public Scrap deleteScrap(Long briefingId, Long memberId) {
        Scrap scrap =
                scrapRepository
                        .findByBriefing_IdAndMember_Id(briefingId, memberId)
                        .orElseThrow(() -> new ScrapException(ErrorCode.SCRAP_NOT_FOUND));
        scrapRepository.delete(scrap);
        return scrap;
    }
}
