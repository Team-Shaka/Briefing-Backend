package briefing.briefing.application.context;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.application.strategy.BriefingQueryStrategy;
import briefing.briefing.domain.Briefing;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BriefingQueryContext {
    private final BriefingQueryStrategy briefingQueryStrategy;

    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        return this.briefingQueryStrategy.findBriefings(params);
    }

}
