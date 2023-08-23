package briefing.briefing.application.dto;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingArticle;
import java.util.List;

public record BriefingDetailResponse(
    Long id,
    Integer rank,
    String title,
    String subtitle,
    String content,
    List<ArticleResponse> articles
) {

  public static BriefingDetailResponse from(final Briefing briefing) {
    final List<ArticleResponse> articleResponses = briefing.getArticles().stream()
        .map(BriefingArticle::getArticle)
        .map(ArticleResponse::from)
        .toList();

    return new BriefingDetailResponse(
        briefing.getId(),
        briefing.getRank(),
        briefing.getTitle(),
        briefing.getSubtitle(),
        briefing.getContent(),
        articleResponses
    );
  }
}
