package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;

import java.util.List;

public interface BriefingQueryStrategy {
    List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params);
}
