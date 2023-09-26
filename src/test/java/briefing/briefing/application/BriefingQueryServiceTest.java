package briefing.briefing.application;

import static briefing.briefing.domain.BriefingType.GLOBAL;
import static briefing.briefing.domain.BriefingType.KOREA;
import static org.assertj.core.api.Assertions.assertThat;

import briefing.briefing.domain.Article;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingArticle;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.repository.ArticleRepository;
import briefing.briefing.domain.repository.BriefingArticleRepository;
import briefing.briefing.domain.repository.BriefingRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(value = "/init.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class BriefingQueryServiceTest {

  @Autowired
  private BriefingQueryService briefingQueryService;
  @Autowired
  private BriefingRepository briefingRepository;
  @Autowired
  private ArticleRepository articleRepository;
  @Autowired
  private BriefingArticleRepository briefingArticleRepository;

  private LocalDate date;
  private List<Briefing> koreaBriefings;
  private List<Briefing> globalBriefings;
  private List<Article> articles;
  private List<BriefingArticle> briefingArticles;
  private Briefing briefing;

  @BeforeEach
  void setUp() {
    date = LocalDate.now();

    koreaBriefings = briefingRepository.saveAll(List.of(
        new Briefing(KOREA, 1, "제목1", "부제목1", "내용1"),
        new Briefing(KOREA, 2, "제목2", "부제목2", "내용2"),
        new Briefing(KOREA, 3, "제목3", "부제목3", "내용3"),
        new Briefing(KOREA, 4, "제목4", "부제목4", "내용4"),
        new Briefing(KOREA, 5, "제목5", "부제목5", "내용5")
    ));
    globalBriefings = briefingRepository.saveAll(List.of(
        new Briefing(GLOBAL, 1, "title1", "subtitle1", "content1"),
        new Briefing(GLOBAL, 2, "title2", "subtitle2", "content2"),
        new Briefing(GLOBAL, 3, "title3", "subtitle3", "content3"),
        new Briefing(GLOBAL, 4, "title4", "subtitle4", "content4"),
        new Briefing(GLOBAL, 5, "title5", "subtitle5", "content5")
    ));

    articles = articleRepository.saveAll(List.of(
        new Article("언론사1", "기사1", "https://url.com"),
        new Article("언론사2", "기사2", "https://url.com")
    ));

    briefing = koreaBriefings.get(0);
    briefingArticles = briefingArticleRepository.saveAll(articles.stream()
        .map(article -> new BriefingArticle(briefing, article))
        .toList());
    briefing.getBriefingArticles().addAll(briefingArticles);
  }

  @ParameterizedTest
  @EnumSource(BriefingType.class)
  @DisplayName("해당하는 type과 date의 브리핑 리스트를 반환한다.")
  void findBriefingsTest(final BriefingType type) {
    //given
    final List<Briefing> briefings = type.equals(KOREA) ? koreaBriefings : globalBriefings;
    final BriefingsResponse expect = BriefingsResponse.from(date, briefings);

    //when
    final BriefingsResponse actual = briefingQueryService.findBriefings(type, date);

    //then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expect);
  }

  @ParameterizedTest
  @EnumSource(BriefingType.class)
  @DisplayName("해당하는 date의 브리핑이 없다면 빈 리스트를 반환한다.")
  void findBriefingsWithNotExistDateTest(final BriefingType type) {
    //given
    final LocalDate notExistDate = date.plusDays(1);
    final BriefingsResponse expect = BriefingsResponse.from(notExistDate, Collections.emptyList());

    //when
    final BriefingsResponse actual = briefingQueryService.findBriefings(type, notExistDate);

    //then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expect);
  }

  @Test
  @DisplayName("브리핑의 상세 정보를 반환한다.")
  void findBriefingTest() {
    //given
    final Long briefingId = briefing.getId();
    final BriefingDetailResponse expect = BriefingDetailResponse.from(briefing);

    //when
    final BriefingDetailResponse actual = briefingQueryService.findBriefing(briefingId);

    //then
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expect);
  }
}
