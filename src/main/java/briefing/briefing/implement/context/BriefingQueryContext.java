package briefing.briefing.implement.context;

import java.util.List;
import java.util.Optional;

import briefing.briefing.domain.Briefing;
import briefing.briefing.implement.strategy.BriefingQueryStrategy;
import briefing.briefing.presentation.dto.BriefingRequestParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BriefingQueryContext {
    private final BriefingQueryStrategy briefingQueryStrategy;

    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        return this.briefingQueryStrategy.findBriefings(params);
    }

    public Optional<Briefing> findById(Long id) {
        return this.briefingQueryStrategy.findById(id);
    }
}
