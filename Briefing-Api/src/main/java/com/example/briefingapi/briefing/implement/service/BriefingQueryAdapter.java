<<<<<<<< HEAD:Briefing-Api/src/main/java/com/example/briefingapi/briefing/implement/service/BriefingQueryAdapter.java
package com.example.briefingapi.briefing.implement.service;
========
package briefing.briefing.application.service;
>>>>>>>> release:Briefing-Api/src/main/java/com/example/briefingapi/briefing/implement/service/BriefingQueryService.java

import java.util.List;

import com.example.briefingapi.annotation.Adapter;
import com.example.briefingapi.briefing.implement.context.BriefingQueryContext;
import com.example.briefingapi.briefing.implement.context.BriefingQueryContextFactory;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.common.exception.BriefingException;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Service;
<<<<<<<< HEAD:Briefing-Api/src/main/java/com/example/briefingapi/briefing/implement/service/BriefingQueryAdapter.java
import lombok.RequiredArgsConstructor;

@Adapter
========

import briefing.briefing.application.context.BriefingQueryContext;
import briefing.briefing.application.context.BriefingQueryContextFactory;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.common.enums.APIVersion;
import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import lombok.RequiredArgsConstructor;

@Service
>>>>>>>> release:Briefing-Api/src/main/java/com/example/briefingapi/briefing/implement/service/BriefingQueryService.java
@RequiredArgsConstructor
public class BriefingQueryAdapter {

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
