package briefing.briefing.application;

import briefing.briefing.application.context.BriefingQueryContext;
import briefing.briefing.application.context.BriefingQueryContextFactory;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.application.strategy.BriefingV1QueryStrategy;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import briefing.common.enums.APIVersion;
import briefing.exception.ErrorCode;
import briefing.exception.handler.BriefingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BriefingQueryService {

  public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params, APIVersion version) {
    BriefingQueryContext briefingQueryContext = BriefingQueryContextFactory.getContextByVersion(version);
    return briefingQueryContext.findBriefings(params);
  }

  public Briefing findBriefing(final Long id, final APIVersion version) {
    BriefingQueryContext briefingQueryContext = BriefingQueryContextFactory.getContextByVersion(version);
    return briefingQueryContext.findById(id)
        .orElseThrow(() -> new BriefingException(ErrorCode.NOT_FOUND_BRIEFING));
  }
}
