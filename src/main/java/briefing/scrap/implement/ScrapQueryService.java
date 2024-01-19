package briefing.scrap.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import briefing.common.exception.common.ErrorCode;
import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import briefing.scrap.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapQueryService {

    private final ScrapRepository scrapRepository;

    public List<Scrap> getScrapsByMemberId(Long memberId) {
        return scrapRepository.findByMember_Id(memberId);
    }

    public Scrap getScrapByBriefingIdAndMemberId(Long briefingId, Long memberId) {
        return scrapRepository
                .findByBriefing_IdAndMember_Id(briefingId, memberId)
                .orElseThrow(() -> new ScrapException(ErrorCode.SCRAP_NOT_FOUND));
    }

    public Boolean existsByMemberIdAndBriefingId(Long memberId, Long briefingId) {
        return scrapRepository.existsByMember_IdAndBriefing_Id(memberId, briefingId);
    }

    public Integer countByBriefingId(Long briefingId) {
        return scrapRepository.countByBriefing_Id(briefingId);
    }
}
