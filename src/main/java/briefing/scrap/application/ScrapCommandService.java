package briefing.scrap.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import briefing.common.enums.APIVersion;
import briefing.scrap.application.context.ScrapCommandContext;
import briefing.scrap.application.context.ScrapCommandContextFactory;
import briefing.scrap.application.dto.ScrapRequest;
import briefing.scrap.domain.Scrap;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapCommandService {

    private final ScrapCommandContextFactory scrapCommandContextFactory;

    public Scrap create(ScrapRequest.CreateDTO request, APIVersion version) {
        ScrapCommandContext scrapCommandContext =
                scrapCommandContextFactory.getContextByVersion(version);
        return scrapCommandContext.create(request);
    }

    public Scrap delete(Long briefingId, Long memberId, APIVersion version) {
        ScrapCommandContext scrapCommandContext =
                scrapCommandContextFactory.getContextByVersion(version);
        return scrapCommandContext.delete(briefingId, memberId);
    }
}
