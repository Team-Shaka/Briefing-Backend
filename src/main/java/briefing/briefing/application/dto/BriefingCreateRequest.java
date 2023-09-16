package briefing.briefing.application.dto;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BriefingCreateRequest(
    @JsonProperty("id") Integer ranks,
    @JsonProperty("keyword") String title,
    String subtitle,
    @JsonProperty("context") String content,
    List<ArticleCreateRequest> articles
) {

  public Briefing toBriefing() {
    return new Briefing(
        BriefingType.KOREA,
        ranks,
        title,
        subtitle,
        content
    );
  }
}
