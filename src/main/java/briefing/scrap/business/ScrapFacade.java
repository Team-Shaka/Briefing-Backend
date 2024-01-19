package briefing.scrap.business;

import org.springframework.stereotype.Component;

import briefing.scrap.implement.ScrapCommandService;
import briefing.scrap.implement.ScrapQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapFacade {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;
}
