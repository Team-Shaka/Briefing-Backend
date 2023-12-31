package briefing.briefing.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.application.context.BriefingQueryContext;
import briefing.briefing.application.context.BriefingQueryContextFactory;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.common.enums.APIVersion;
import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
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
