package briefing.scrap.application;

import briefing.scrap.domain.Scrap;
import briefing.scrap.domain.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapQueryService {

    private final ScrapRepository scrapRepository;

    public List<Scrap> getScrapsByMemberId(Long memberId) {
        return scrapRepository.findByMember_Id(memberId);
    }


    public Boolean existsByMemberIdAndBriefingId(Long memberId, Long briefingId) {
        return scrapRepository.existsByMember_IdAndBriefing_Id(memberId, briefingId);
    }
}
