package briefing.briefing.application.context;

import briefing.briefing.application.strategy.BriefingQueryStrategy;
import briefing.briefing.application.strategy.BriefingV1QueryStrategy;
import briefing.briefing.application.strategy.BriefingV2QueryStrategy;
import briefing.briefing.domain.repository.BriefingRepository;
import briefing.common.enums.APIVersion;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BriefingQueryContextFactory {

    private final BriefingRepository briefingRepository;
    private static BriefingQueryContext staticBriefingQueryContextV1;
    private static BriefingQueryContext staticBriefingQueryContextV2;

    @PostConstruct
    private void init() {
        staticBriefingQueryContextV1 = createContext(new BriefingV1QueryStrategy(briefingRepository));
        staticBriefingQueryContextV2 = createContext(new BriefingV2QueryStrategy(briefingRepository));
    }

    private static BriefingQueryContext createContext(BriefingQueryStrategy strategy) {
        return new BriefingQueryContext(strategy);
    }

    public static BriefingQueryContext getContextByVersion(APIVersion version) {
        return switch (version) {
            case V1 -> staticBriefingQueryContextV1;
            case V2 -> staticBriefingQueryContextV2;
        };
    }
}

