package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;

import java.util.List;
import java.util.Optional;

public interface BriefingQueryStrategy {
    List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params);

    Optional<Briefing> findById(Long id);
}
