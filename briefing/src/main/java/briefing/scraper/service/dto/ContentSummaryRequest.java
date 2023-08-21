package briefing.scraper.service.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ContentSummaryRequest {

  private final String model = "gpt-3.5-turbo-16k";
  private final double temperature = 0.48;
  private final List<BriefingMessageRequest> messages = new ArrayList<>();

  public ContentSummaryRequest(final String content) {
    messages.add(new BriefingMessageRequest("system",
        "You are a news summary expert. Summarize news in 6lines. IN Korean"));
    messages.add(new BriefingMessageRequest("user", content));
  }

  record BriefingMessageRequest(String role, String content) {

  }
}
