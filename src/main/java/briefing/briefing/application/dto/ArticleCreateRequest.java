package briefing.briefing.application.dto;

import briefing.briefing.domain.Article;

public record ArticleCreateRequest(
    String press,
    String title,
    String url
) {

  public Article toArticle() {
    return new Article(press, title, url);
  }
}
