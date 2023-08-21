package briefing.briefing.scraper;

import briefing.SpringBootTestHelper;
import briefing.briefing.service.dto.ScrapResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.yml")
class NaverNewsScraperTest extends SpringBootTestHelper {

  @Autowired
  private NaverNewsScraper scraper;

  @Test
  void scraper() {
    final String url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=104";

    final List<ScrapResponse> responses = scraper.scrapHeadline(url);
    for (final ScrapResponse response : responses) {
      System.out.println("response.getPress() = " + response.getPress());
      System.out.println("response.getTitle() = " + response.getTitle());
      System.out.println("response.getUrl() = " + response.getUrl());
    }
  }

}
