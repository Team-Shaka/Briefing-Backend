package briefing.briefing.service.dto;

import java.util.List;

public record BriefingContentResponse(
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
