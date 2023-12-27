package briefing.briefing.application.strategy;

import java.util.List;
import java.util.Optional;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.common.enums.APIVersion;

public interface BriefingQueryStrategy {
List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params);

Optional<Briefing> findById(Long id);

APIVersion getVersion();
}
