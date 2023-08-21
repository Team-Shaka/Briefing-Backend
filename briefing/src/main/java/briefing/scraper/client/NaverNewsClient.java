package briefing.scraper.client;

import briefing.scraper.client.dto.ArticleScrapResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverNewsClient {

  public List<ArticleScrapResponse> scrapHeadline(final String url) {
    final String newsSelector = "#main_content > div > div._persist > div.section_headline > ul > li > div.sh_text";
    final Connection connect = Jsoup.connect(url);

    try {
      final Document document = connect.get();

      return document.select(newsSelector).stream()
          .map(ArticleScrapResponse::new)
          .toList();
    } catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public String scrapDetailContent(final String url) {
    final String selector = "#dic_area";
    final Connection connect = Jsoup.connect(url);

    try {
      final Document document = connect.get();

      return document.selectFirst(selector).text();
    } catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }
}
