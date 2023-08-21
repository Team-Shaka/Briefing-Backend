package briefing.scraper.service;

import briefing.scraper.client.NaverNewsClient;
import briefing.scraper.client.dto.ArticleScrapResponse;
import briefing.scraper.service.dto.ArticleSummaryContentResponse;
import briefing.scraper.service.dto.ArticleSummaryContentResponse.Keyword;
import briefing.scraper.service.dto.ArticleSummaryRequest;
import briefing.scraper.service.dto.ArticleSummaryResponse;
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

    final List<ArticleScrapResponse> koreaResponses = extractScrapResponses(koreaUrls);
    //final List<ScrapResponse> globalResponses = extractScrapResponses(globalUrls);
    //
    //final String koreaContent = convertToContentString(koreaResponses);
    //final String globalContent = convertToContentString(globalResponses);
    //
    //final BriefingContentResponse koreaBriefingContentResponse = getBriefingContentResponse(
    //    koreaContent);
    //final BriefingContentResponse globalBriefingContentResponse = getBriefingContentResponse(
    //    globalContent);
    //
    final ArticleSummaryContentResponse koreaArticleSummaryContentResponse = getBriefingContentResponse(
        null);
    final ArticleSummaryContentResponse globalArticleSummaryContentResponse = getBriefingContentResponse(
        null);

    for (final Keyword keyword : koreaArticleSummaryContentResponse.keywords()) {
      final String title = keyword.keyword();
      final String subtitle = keyword.subtitle();
      final List<ArticleScrapResponse> articles = keyword.articles().stream()
          .map(article -> koreaResponses.get(article.id() - 1))
          .toList();

      final String content = articles.stream()
          .map(article -> scraper.scrapDetailContent(article.getUrl()))
          .collect(Collectors.joining("\n"));


    }
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

//curl --location --request POST 'https://api.openai.com/v1/chat/completions' \
//--header 'Authorization: Bearer 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' \
//--header 'Content-Type: application/json' \
//--data-raw '{
//    "model": "gpt-3.5-turbo-16k",
//    "temperature": 0.48,
//    "messages": [
//      {
//        "role": "system",
//        "content": "You are a news summary expert. Summarize news in 6lines. IN Korean"
//      },
//      {
//        "role": "user",
//        "content": "윤석열 대통령이 한미일 정상회의 성과를 직접 국민에게 설명합니다. 윤 대통령은 21일 오전 용산 대통령실에서 열릴 국무회의 모두발언을 통해 캠프 데이비드에서의 한미일 정상회의 성과를 직접 국민에게 설명한다고 밝혔습니다. 윤 대통령은 이날 오전 10시 을지 국무회의와 제35회 정례 국무회의를 차례로 주재할 예정으로, 해당 회의는 생방송으로 중계됩니다. 을지 국무회의는 이날부터 3박 4일 동안 실시되는 범정부 훈련인 을지연습과 관련해 국가비상사태 시 정부 대응 태세를 점검하기 위해 마련됐습니다. 윤 대통령은 앞서 이날 오전 9시에는 용산 대통령실에서 을지 국가안전보장회의(NSC)도 직접 주재할 예정입니다. 윤석열 대통령이 21일 “캠프 데이비드 3국 정상회의를 계기로 한·미·일 협력의 새로운 시대가 열렸다. 우리 국민이 체감할 3국 협력의 혜택과 이득도 더욱 증대될 것”이라고 한·미·일 정상회의 성과를 강조했다. 윤 대통령은 이날 용산 대통령실에서 을지 국무회의를 주재하고 “이번 캠프 데이비드 한·미·일 정상회의는 한·미·일 3국의 포괄적 협력 체계를 제도화하고 공고화했다”며 이렇게 말했다. 윤 대통령은 3국 정상회의 참석차 미국에서 1박4일의 일정을 마치고 지난 20일 귀국했다. 그는 “3국 정상들은 최소 1년에 한번 모이기로 했다”며 “한반도 역내 공조에 머물렀던 한·미·일 협력은 인도·태평양 지역 전반의 자유, 평화, 번영을 구축하는 데 기여하는 범지역 협력체로 진화할 것이다. 협력 분야도 안보뿐 아니라 사이버, 경제, 첨단 기술, 개발협력, 보건, 여성, 인적 교류를 망라한 포괄적 협력체를 지향하게 될 것”이라고 설명했다. 또 “북한의 도발 위협이 커지면 커질수록 한미일 3각 안보 협력의 결정체 구조는 더욱 견고해질 것”이라며 “3각 협력 결정체 구조는 북한의 도발 위험을 낮추고 우리의 안보를 더욱 튼튼하게 할 것”이라고도 했다. 윤 대통령은 이번 정상회의의 또 다른 성과로 △공급망 조기 경보 시스템 구축 △인공지능(AI) 기술 사용 관련 국제규범 논의 가속화 △개발금융기관 간 양해각서(MOU) 체결 △한·미·일 3국 청년 서밋 신설 등을 거론하며 “우리 국민들에게 위험은 확실하게 줄어들고 기회는 확실하게 커질 것”이라고 말했다. 그는 이런 성과로 인해 “요소수 사태와 같은 외부 교란 요인 발생 시 신속한 공조 대응이 가능해진다. 우리 기업과 국민이 진출할 수 있는 시장의 규모와 회복력이 더 커진다”고 전망했다. 또 “대한민국의 미래성장동력 확보와 양질의 고소득 일자리 창출에 크게 기여할 것”이라며 “각 부처는 한·미·일 협력체계의 성과를 국민이 체감할 수 있도록 만전을 기해달라”고 당부했다."
//      }
//    ]
//}
//'
