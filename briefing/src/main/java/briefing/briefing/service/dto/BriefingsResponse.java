package briefing.briefing.service.dto;

import briefing.briefing.domain.Briefing;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

public record BriefingsResponse(
    @JsonProperty("created_at") LocalDate createdAt,
    @JsonProperty("briefings") List<BriefingSimpleResponse> briefings
) {

  public static BriefingsResponse from(final LocalDate date, final List<Briefing> briefings) {
    final List<BriefingSimpleResponse> briefingSimpleResponses = briefings.stream()
        .map(BriefingSimpleResponse::from)
        .toList();

    return new BriefingsResponse(date, briefingSimpleResponses);
  }
}
