package briefing.scrap.application.strategy;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import briefing.common.enums.APIVersion;
import briefing.exception.ErrorCode;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.application.strategy.helper.ScrapCreationHelper;
import briefing.scrap.application.strategy.helper.ScrapDeletionHelper;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import briefing.scrap.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapV1CommandStrategy implements ScrapCommandStrategy {

    private final ScrapCreationHelper scrapCreationHelper;
    private final ScrapDeletionHelper scrapDeletionHelper;
    private final ScrapRepository scrapRepository;

    @Override
    public Scrap create(ScrapRequest.CreateDTO request) {
        Scrap scrap = scrapCreationHelper.createScrap(request);

        try {
            // Scrap 엔티티 저장 및 반환
            return scrapRepository.save(scrap);
        } catch (DataIntegrityViolationException e) {
            // 중복 스크랩 예외 처리
            throw new ScrapException(ErrorCode.DUPLICATE_SCRAP);
        }
    }

    @Override
    public Scrap delete(Long briefingId, Long memberId) {
        return scrapDeletionHelper.deleteScrap(briefingId, memberId);
    }

    @Override
    public APIVersion getVersion() {
        return APIVersion.V1;
    }
}
