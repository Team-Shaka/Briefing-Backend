package briefing.briefing.application.dto;

import briefing.briefing.domain.Briefing;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;

public record BriefingsResponse(
    @JsonProperty("created_at") LocalDate createdAt,
    List<BriefingResponse> briefings
) {

  public static BriefingsResponse from(final LocalDate date, final List<Briefing> briefings) {
    final List<BriefingResponse> briefingResponses = briefings.stream()
        .map(BriefingResponse::from)
        .toList();

    return new BriefingsResponse(date, briefingResponses);
  }
}
