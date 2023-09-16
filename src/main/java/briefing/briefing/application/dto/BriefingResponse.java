package briefing.briefing.application.dto;

import briefing.briefing.domain.Briefing;

public record BriefingResponse(
    Long id,
    Integer ranks,
    String title,
    String subtitle
) {

  public static BriefingResponse from(final Briefing briefing) {
    return new BriefingResponse(
        briefing.getId(),
        briefing.getRanks(),
        briefing.getTitle(),
        briefing.getSubtitle()
    );
  }
}
