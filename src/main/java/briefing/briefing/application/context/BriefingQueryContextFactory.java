package briefing.briefing.application.context;

import briefing.briefing.application.strategy.BriefingQueryStrategy;
import briefing.briefing.application.strategy.BriefingV1QueryStrategy;
import briefing.briefing.application.strategy.BriefingV2QueryStrategy;
import briefing.briefing.domain.repository.BriefingRepository;
import briefing.common.enums.APIVersion;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class BriefingQueryContextFactory {

    private final Map<APIVersion, BriefingQueryContext> contextMap;

    @Autowired
    public BriefingQueryContextFactory(List<BriefingQueryStrategy> strategies) {
        contextMap = new EnumMap<>(APIVersion.class);
        for (BriefingQueryStrategy strategy : strategies) {
            APIVersion version = strategy.getVersion();
            contextMap.put(version, new BriefingQueryContext(strategy));
        }
    }

    public BriefingQueryContext getContextByVersion(APIVersion version) {
        BriefingQueryContext context = contextMap.get(version);
        if (context == null) {
            throw new IllegalArgumentException("Invalid API version: " + version);
        }
        return context;
    }
}

