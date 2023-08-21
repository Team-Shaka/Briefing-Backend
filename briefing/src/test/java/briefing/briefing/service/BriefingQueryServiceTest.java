package briefing.briefing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import briefing.SpringBootTestHelper;
import briefing.briefing.domain.Article;
import briefing.briefing.domain.ArticleRepository;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingRepository;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.service.dto.BriefingsResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BriefingQueryServiceTest extends SpringBootTestHelper {

  @Autowired
  private BriefingQueryService briefingQueryService;
  @Autowired
  private BriefingRepository briefingRepository;
  @Autowired
  private ArticleRepository articleRepository;

  private LocalDate date;
  private BriefingType koreaType;
  private BriefingType globalType;
  private List<Briefing> koreaBriefings;
  private List<Briefing> globalBriefings;
  private List<Article> koreaBriefingArticles;
  private List<Article> globalBriefingArticles;


  @BeforeEach
  void setUp() {
    date = LocalDate.now();
    koreaType = BriefingType.KOREA;
    globalType = BriefingType.GLOBAL;

    koreaBriefings = List.of(
        new Briefing(koreaType, 1, "제목 1", "부제목 1", "내용 1"),
        new Briefing(koreaType, 2, "제목 2", "부제목 2", "내용 2"),
        new Briefing(koreaType, 3, "제목 3", "부제목 3", "내용 3"),
        new Briefing(koreaType, 4, "제목 4", "부제목 4", "내용 4")
    );
    globalBriefings = List.of(
        new Briefing(globalType, 1, "title 1", "subtitle 1", "content 1"),
        new Briefing(globalType, 2, "title 2", "subtitle 2", "content 2"),
        new Briefing(globalType, 3, "title 3", "subtitle 3", "content 3"),
        new Briefing(globalType, 4, "title 4", "subtitle 4", "content 4")
    );

    briefingRepository.saveAll(koreaBriefings);
    briefingRepository.saveAll(globalBriefings);

    koreaBriefingArticles = koreaBriefings.stream()
        .map(briefing -> new Article("언론사", "제목", "https://article-url.com", briefing))
        .toList();
    globalBriefingArticles = globalBriefings.stream()
        .map(briefing -> new Article("언론사", "제목", "https://article-url.com", briefing))
        .toList();

    articleRepository.saveAll(koreaBriefingArticles);
    articleRepository.saveAll(globalBriefingArticles);
  }

  @Nested
  @DisplayName("브리핑 리스트 테스트")
  class FindBriefingsTest {

    @Test
    @DisplayName("Korea로 필터링된 브리핑 리스트를 반환한다.")
    void findBriefingsWithKoreaTypeTest() {
      //given
      final String type = koreaType.getValue();
      final BriefingsResponse expect = BriefingsResponse.from(date, koreaBriefings);

      //when
      final BriefingsResponse actual = briefingQueryService.findBriefings(type, date);

      //then
      assertThat(actual)
          .usingRecursiveComparison()
          .isEqualTo(expect);
    }

    @Test
    @DisplayName("Global로 필터링된 브리핑 리스트를 반환한다.")
    void findBriefingsWithGlobalTypeTest() {
      //given
      final String type = globalType.getValue();
      final BriefingsResponse expect = BriefingsResponse.from(date, globalBriefings);

      //when
      final BriefingsResponse actual = briefingQueryService.findBriefings(type, date);

      //then
      assertThat(actual)
          .usingRecursiveComparison()
          .isEqualTo(expect);
    }

    @Test
    @DisplayName("해당 날짜의 데이터가 존재하지 않을 경우 빈 리스트를 반환한다.")
    void findBriefingsWithNotExistDateTest() {
      //given
      final String type = globalType.getValue();
      final LocalDate notExistDate = LocalDate.now().plusDays(1L);
      final BriefingsResponse expect = BriefingsResponse.from(notExistDate, new ArrayList<>());

      //when
      final BriefingsResponse actual = briefingQueryService.findBriefings(type, notExistDate);

      //then
      assertThat(actual)
          .usingRecursiveComparison()
          .isEqualTo(expect);
    }

    @Test
    @DisplayName("해당 타입이 존재하지 않을 경우 빈 Exception이 발생한다.")
    void findBriefingsWithNotExistTypeExceptionTest() {
      //given
      final String notExistType = "존재하지 않는 타입";

      //when & then
      assertThrowsExactly(
          IllegalArgumentException.class,
          () -> briefingQueryService.findBriefings(notExistType, date)
      );
    }
  }
}
