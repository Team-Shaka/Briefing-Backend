package briefing.scraper.client;

import briefing.scraper.service.dto.ScrapResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverNewsClient {

  public List<ScrapResponse> scrapHeadline(final String url) {
    final String newsSelector = "#main_content > div > div._persist > div.section_headline > ul > li > div.sh_text";
    final Connection connect = Jsoup.connect(url);

    try {
      final Document document = connect.get();

      return document.select(newsSelector).stream()
          .map(ScrapResponse::new)
          .toList();
    } catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
