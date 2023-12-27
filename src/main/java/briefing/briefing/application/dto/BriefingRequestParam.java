package briefing.briefing.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BriefingRequestParam {

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public static class BriefingPreviewListParam {
	@NotNull private BriefingType type;
	private LocalDate date;
	private TimeOfDay timeOfDay = TimeOfDay.MORNING;

	public boolean isPresentDate() {
	return date != null;
	}
}
}
