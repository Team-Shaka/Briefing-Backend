package briefing.briefing.service;

import briefing.SpringBootTestHelper;
import briefing.scraper.service.ScrapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScrapServiceTest extends SpringBootTestHelper {

  @Autowired
  private ScrapService scrapService;

  @Test
  void name() {
    scrapService.updateBriefing();
  }
}
