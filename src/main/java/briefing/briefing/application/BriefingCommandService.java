package briefing.briefing.application;

import briefing.briefing.api.BriefingConverter;
import briefing.briefing.application.dto.BriefingRequestDTO;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingArticle;
import briefing.briefing.domain.repository.ArticleRepository;
import briefing.briefing.domain.repository.BriefingArticleRepository;
import briefing.briefing.domain.repository.BriefingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BriefingCommandService {

  private final BriefingRepository briefingRepository;
  private final ArticleRepository articleRepository;
  private final BriefingArticleRepository briefingArticleRepository;

  public void createBriefing(final BriefingRequestDTO.BriefingCreate request) {
    final Briefing briefing = BriefingConverter.toBriefing(request);
    final List<Article> articles = request.getArticles().stream()
        .map(BriefingConverter::toArticle)
        .toList();

    briefingRepository.save(briefing);
    articleRepository.saveAll(articles);

    final List<BriefingArticle> briefingArticles = articles.stream()
        .map(article -> new BriefingArticle(briefing, article))
        .toList();
    briefingArticleRepository.saveAll(briefingArticles);
  }
}
