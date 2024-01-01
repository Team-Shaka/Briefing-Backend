package briefing.scrap.application.context;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import briefing.common.enums.APIVersion;
import briefing.scrap.application.strategy.ScrapCommandStrategy;

@Component
public class ScrapCommandContextFactory {

    private final Map<APIVersion, ScrapCommandContext> contextMap;

    @Autowired
    public ScrapCommandContextFactory(List<ScrapCommandStrategy> strategies) {
        contextMap = new EnumMap<>(APIVersion.class);
        for (ScrapCommandStrategy strategy : strategies) {
            APIVersion version = strategy.getVersion();
            contextMap.put(version, new ScrapCommandContext(strategy));
        }
    }

    public ScrapCommandContext getContextByVersion(APIVersion version) {
        ScrapCommandContext context = contextMap.get(version);
        if (context == null) {
            throw new IllegalArgumentException("Invalid API version: " + version);
        }
        return context;
    }
}
