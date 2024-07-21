package com.example.briefingapi.briefing.implement.context;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.example.briefingapi.briefing.implement.strategy.BriefingQueryStrategy;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
