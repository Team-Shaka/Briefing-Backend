package briefing.briefing.implement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import briefing.briefing.domain.Briefing;
import briefing.briefing.exception.BriefingException;
import briefing.briefing.implement.context.BriefingQueryContext;
import briefing.briefing.implement.context.BriefingQueryContextFactory;
import briefing.briefing.presentation.dto.BriefingRequestParam;
import briefing.common.enums.APIVersion;
import briefing.common.exception.common.ErrorCode;
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
