package briefing.briefing.service.dto;

import briefing.briefing.domain.Briefing;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BriefingSimpleResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("type") String type,
    @JsonProperty("rank") Integer rank,
    @JsonProperty("title") String title,
    @JsonProperty("subtitle") String subtitle
) {

  public static BriefingSimpleResponse from(final Briefing briefing) {
    return new BriefingSimpleResponse(
        briefing.getId(),
        briefing.getType().getValue(),
        briefing.getRank(),
        briefing.getTitle(),
        briefing.getSubtitle()
    );
  }
}
