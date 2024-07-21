package com.example.briefingapi.briefing.implement.context;

import java.util.List;
import java.util.Optional;



import com.example.briefingapi.briefing.implement.strategy.BriefingQueryStrategy;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.entity.Briefing;
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
