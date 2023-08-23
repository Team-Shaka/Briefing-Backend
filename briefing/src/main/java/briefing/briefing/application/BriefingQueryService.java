package briefing.briefing.application;

import briefing.briefing.application.dto.BriefingsResponse;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.repository.BriefingRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BriefingQueryService {

  private final BriefingRepository briefingRepository;

  public BriefingsResponse findBriefings(final BriefingType type, final LocalDate date) {
    final LocalDateTime startDateTime = date.atStartOfDay();
    final LocalDateTime endDateTime = date.atTime(LocalTime.MAX);

    final List<Briefing> briefings = briefingRepository.findAllByTypeAndCreatedAtBetween(type,
        startDateTime, endDateTime);

    return BriefingsResponse.from(date, briefings);
  }
}
