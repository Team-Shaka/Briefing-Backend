package com.example.briefingapi.briefing.implement.service;

import java.util.List;

import com.example.briefingapi.briefing.implement.context.BriefingQueryContext;
import com.example.briefingapi.briefing.implement.context.BriefingQueryContextFactory;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.common.exception.BriefingException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingQueryService {

    private final BriefingQueryContextFactory briefingQueryContextFactory;

    public List<Briefing> findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params, APIVersion version) {
        BriefingQueryContext briefingQueryContext =
                briefingQueryContextFactory.getContextByVersion(version);
        return briefingQueryContext.findBriefings(params);
    }

    public Briefing findBriefing(final Long id, final APIVersion version) {
        BriefingQueryContext briefingQueryContext =
                briefingQueryContextFactory.getContextByVersion(version);
        return briefingQueryContext
                .findById(id)
                .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_BRIEFING));
    }
}
