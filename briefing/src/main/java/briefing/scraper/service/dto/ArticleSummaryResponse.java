package briefing.scraper.service.dto;

import java.util.List;

public record ArticleSummaryResponse(
    String id,
    String object,
    Long created,
    String model,
    List<Choice> choices
) {

  public record Choice(
      Integer index,
      Message message
  ) {

  }

  public record Message(
      String role,
      String content
  ) {

  }
}
