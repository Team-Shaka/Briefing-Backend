package briefing.scraper.service.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticleSummaryRequest {

  private final String model = "gpt-4";
  private final int temperature = 1;
  private final List<BriefingMessageRequest> messages = new ArrayList<>();

  public ArticleSummaryRequest(final String content) {
    messages.add(new BriefingMessageRequest("system",
        "You are a news keyword finder. I will give you some article titles and id of the articles. You have to see all of article titles, and pick 10 keywords that represents that day. And, give it to me with subtitle, and 2 articles which are reason of picking the keyword with id. And you have to give it to me with json format, like example. this is example -> {keywords:[{index: 1,keyword: (keyword),subtitle: (subtitle),articles: [{id: (id of article), title: (article title)}, {id: (id of article), title: (article title)}]},{index: 2,keyword: (keyword),subtitle: (subtitle),articles: [{{id: (id of article), title: (article title)}, {id: (id of article), title: (article title)}]},...{index: 10,keyword: (keyword),subtitle: (subtitle),articles: [{id: (id of article), title: (article title)}, {id: (id of article), title: (article title)}]}]}"));
    messages.add(new BriefingMessageRequest("user", content));
  }

  record BriefingMessageRequest(String role, String content) {

  }
}
