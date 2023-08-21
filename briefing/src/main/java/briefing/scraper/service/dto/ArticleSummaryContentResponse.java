package briefing.scraper.service.dto;

import java.util.List;

public record ArticleSummaryContentResponse(
    List<Keyword> keywords
) {

  public record Keyword(
      Integer index,
      String keyword,
      String subtitle,
      List<Article> articles
  ) {

  }

  public record Article(
      Integer id,
      String title
  ) {

  }
}
