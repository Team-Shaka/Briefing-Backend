package com.example.briefingapi.briefing.implement.strategy;

import java.util.List;
import java.util.Optional;


import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.APIVersion;

public interface BriefingQueryStrategy {
    List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params);

    Optional<Briefing> findById(Long id);

    APIVersion getVersion();
}
