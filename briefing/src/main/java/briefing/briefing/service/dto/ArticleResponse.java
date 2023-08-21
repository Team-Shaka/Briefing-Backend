package briefing.briefing.service.dto;

import briefing.briefing.domain.Article;

public record ArticleResponse(
    Long id,
    String press,
    String title,
    String url
) {

  public static ArticleResponse from(final Article article) {
    return new ArticleResponse(
        article.getId(),
        article.getPress(),
        article.getTitle(),
        article.getUrl()
    );
  }
}
