package briefing.scraper.service;

import briefing.briefing.domain.Article;
import briefing.briefing.domain.ArticleRepository;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingRepository;
import briefing.briefing.domain.BriefingType;
import briefing.scraper.client.NaverNewsClient;
import briefing.scraper.client.dto.ArticleScrapResponse;
import briefing.scraper.service.dto.ArticleSummaryContentResponse;
import briefing.scraper.service.dto.ArticleSummaryContentResponse.Keyword;
import briefing.scraper.service.dto.ArticleSummaryRequest;
import briefing.scraper.service.dto.ArticleSummaryResponse;
import briefing.scraper.service.dto.ContentSummaryRequest;
import briefing.scraper.service.dto.ContentSummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {

  private final BriefingRepository briefingRepository;
  private final ArticleRepository articleRepository;
  private final NaverNewsClient scraper;
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  @Value("${scrap.url.정치}")
  private String 정치;
  @Value("${scrap.url.경제}")
  private String 경제;
  @Value("${scrap.url.사회}")
  private String 사회;
  @Value("${scrap.url.생활_문화}")
  private String 생활_문화;
  @Value("${scrap.url.IT_과학}")
  private String IT_과학;
  @Value("${scrap.url.세계}")
  private String 세계;
  @Value("${openai.token}")
  private String token;
  @Value("${openai.url.chat}")
  private String chatUrl;

  public void updateBriefing() {
    final List<String> koreaUrls = List.of(정치, 경제, 사회, 생활_문화, IT_과학);
    final List<String> globalUrls = List.of(세계, 세계, 세계, 세계);

    saveBriefingsAndArticles(BriefingType.KOREA, koreaUrls);
    saveBriefingsAndArticles(BriefingType.GLOBAL, globalUrls);
  }

  private void saveBriefingsAndArticles(final BriefingType type, final List<String> urls) {
    final List<ArticleScrapResponse> scrapResponses = extractScrapResponses(urls);

    final String articleContentString = convertToContentString(scrapResponses);

    final ArticleSummaryContentResponse articleSummaryContentResponse = getBriefingContentResponse(
        articleContentString);

    for (final Keyword keyword : articleSummaryContentResponse.keywords()) {
      final Integer rank = keyword.index();
      final String title = keyword.keyword();
      final String subtitle = keyword.subtitle();
      final List<ArticleScrapResponse> articles = keyword.articles().stream()
          .map(article -> scrapResponses.get(article.id() - 1))
          .toList();

      final String content = articles.stream()
          .map(article -> scraper.scrapDetailContent(article.getUrl()))
          .collect(Collectors.joining("\n"));

      final HttpHeaders headers = new HttpHeaders();
      headers.setBearerAuth(token);
      headers.setContentType(MediaType.APPLICATION_JSON);

      final ContentSummaryRequest contentSummaryRequest = new ContentSummaryRequest(content);
      final HttpEntity<ContentSummaryRequest> requestEntity = new HttpEntity<>(
          contentSummaryRequest, headers);

      final String summerizedContent = getSummerizedContent(requestEntity);

      final Briefing briefing = new Briefing(type, rank, title, subtitle,
          summerizedContent);
      final List<Article> articleDomains = articles.stream()
          .map(article -> new Article(article.getPress(), article.getTitle(), article.getUrl(),
              briefing))
          .toList();

      briefingRepository.save(briefing);
      articleRepository.saveAll(articleDomains);
    }
  }

  private String getSummerizedContent(final HttpEntity<ContentSummaryRequest> requestEntity) {
    final ContentSummaryResponse contentSummaryResponse = restTemplate
        .exchange(chatUrl, HttpMethod.POST, requestEntity, ContentSummaryResponse.class)
        .getBody();
    final String summerizedContent = contentSummaryResponse.choices().get(0).message().content();
    return summerizedContent.substring(0, Math.min(summerizedContent.length(), 1000));
  }

  private ArticleSummaryContentResponse getBriefingContentResponse(final String koreaContent) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    final ArticleSummaryRequest articleSummaryRequest = new ArticleSummaryRequest(koreaContent);
    final HttpEntity<ArticleSummaryRequest> requestEntity = new HttpEntity<>(
        articleSummaryRequest, headers);

    final ArticleSummaryResponse body = restTemplate
        //.exchange(chatUrl, HttpMethod.POST, requestEntity, BriefingSummaryResponse.class)
        .exchange("https://7ab7c6c1-9228-4cb2-b19c-774d9cd8b73d.mock.pstmn.io/summary",
            HttpMethod.POST, requestEntity, ArticleSummaryResponse.class)
        .getBody();

    final String content = body.choices().get(0).message().content();

    try {
      return objectMapper.readValue(content, ArticleSummaryContentResponse.class);
    } catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private String convertToContentString(final List<ArticleScrapResponse> koreaResponses) {
    return koreaResponses.stream()
        .map(ArticleScrapResponse::toStringIdAndTitle)
        .collect(Collectors.joining(", "));
  }

  private List<ArticleScrapResponse> extractScrapResponses(final List<String> urls) {
    long id = 1;
    final List<ArticleScrapResponse> responses = new ArrayList<>();

    urls.forEach(url -> responses.addAll(scraper.scrapHeadline(url)));

    for (final ArticleScrapResponse response : responses) {
      response.setId(id++);
    }

    return responses;
  }
}
