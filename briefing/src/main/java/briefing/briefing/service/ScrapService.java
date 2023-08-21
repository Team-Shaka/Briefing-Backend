package briefing.briefing.service;

import briefing.briefing.scraper.NaverNewsScraper;
import briefing.briefing.service.dto.BriefingContentResponse;
import briefing.briefing.service.dto.BriefingSummaryRequest;
import briefing.briefing.service.dto.BriefingSummaryResponse;
import briefing.briefing.service.dto.ScrapResponse;
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

  private final NaverNewsScraper scraper;
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

    final List<ScrapResponse> koreaResponses = extractScrapResponses(koreaUrls);
    final List<ScrapResponse> globalResponses = extractScrapResponses(globalUrls);

    final String koreaContent = convertToContentString(koreaResponses);
    final String globalContent = convertToContentString(globalResponses);

    final BriefingContentResponse koreaBriefingContentResponse = getBriefingContentResponse(
        koreaContent);
    final BriefingContentResponse globalBriefingContentResponse = getBriefingContentResponse(
        globalContent);

    System.out.println("koreaBriefingContentResponse = " + koreaBriefingContentResponse);
    System.out.println("globalBriefingContentResponse = " + globalBriefingContentResponse);
  }

  private BriefingContentResponse getBriefingContentResponse(final String koreaContent) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    final BriefingSummaryRequest briefingSummaryRequest = new BriefingSummaryRequest(koreaContent);
    final HttpEntity<BriefingSummaryRequest> requestEntity = new HttpEntity<>(
        briefingSummaryRequest, headers);

    final BriefingSummaryResponse body = restTemplate
        .exchange(chatUrl, HttpMethod.POST, requestEntity, BriefingSummaryResponse.class)
        .getBody();

    final String content = body.choices().get(0).message().content();

    try {
      return objectMapper.readValue(content, BriefingContentResponse.class);
    } catch (final Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private String convertToContentString(final List<ScrapResponse> koreaResponses) {
    return koreaResponses.stream()
        .map(ScrapResponse::toStringIdAndTitle)
        .collect(Collectors.joining(", "));
  }

  private List<ScrapResponse> extractScrapResponses(final List<String> urls) {
    long id = 1;
    final List<ScrapResponse> responses = new ArrayList<>();

    urls.forEach(url -> responses.addAll(scraper.scrapHeadline(url)));

    for (final ScrapResponse response : responses) {
      response.setId(id++);
    }

    return responses;
  }
}
