package briefing.scraper.client.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsoup.nodes.Element;

@Getter
@ToString
public class ArticleScrapResponse {

  private static final String TITLE_SELECTOR = "div.sh_text > a";
  private static final String PRESS_SELECTOR = "div.sh_text > div.sh_text_info > div";
  private static final String URL_EXTRACT_ATTRIBUTE = "href";

  private final String press;
  private final String title;
  private final String url;
  @Setter
  private Long id;

  public ArticleScrapResponse(final Element element) {
    this.press = element.select(PRESS_SELECTOR).text();
    this.title = element.select(TITLE_SELECTOR).text();
    this.url = element.select(TITLE_SELECTOR).attr(URL_EXTRACT_ATTRIBUTE);
  }

  public String toStringIdAndTitle() {
    return "{id=" + id + ", title=" + title.replaceAll("\"", "'") + "}";
  }
}
