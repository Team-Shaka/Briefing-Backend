package briefing.briefing.application.context;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import briefing.briefing.application.strategy.BriefingQueryStrategy;
import briefing.common.enums.APIVersion;

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
