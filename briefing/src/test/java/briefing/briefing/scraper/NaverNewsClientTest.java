package briefing.briefing.scraper;

import briefing.SpringBootTestHelper;
import briefing.scraper.client.NaverNewsClient;
import briefing.scraper.client.dto.ArticleScrapResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.yml")
class NaverNewsClientTest extends SpringBootTestHelper {

  @Autowired
  private NaverNewsClient scraper;

  @Test
  void scraper() {
    final String url = "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=104";

    final List<ArticleScrapResponse> responses = scraper.scrapHeadline(url);
    for (final ArticleScrapResponse response : responses) {
      System.out.println("response.getPress() = " + response.getPress());
      System.out.println("response.getTitle() = " + response.getTitle());
      System.out.println("response.getUrl() = " + response.getUrl());
    }
  }

}
