package briefing.briefing.implement.strategy;

import java.util.List;
import java.util.Optional;

import briefing.briefing.domain.Briefing;
import briefing.briefing.presentation.dto.BriefingRequestParam;
import briefing.common.enums.APIVersion;

public interface BriefingQueryStrategy {
    List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params);

    Optional<Briefing> findById(Long id);

    APIVersion getVersion();
}
