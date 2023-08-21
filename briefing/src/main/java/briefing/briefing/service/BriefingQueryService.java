package briefing.briefing.service;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingRepository;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.service.dto.BriefingDetailResponse;
import briefing.briefing.service.dto.BriefingsResponse;
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

  public BriefingsResponse findBriefings(final String type, final LocalDate date) {
    final BriefingType briefingType = BriefingType.from(type);
    final LocalDateTime startDateTime = date.atStartOfDay();
    final LocalDateTime endDateTime = date.atTime(LocalTime.MAX);

    final List<Briefing> briefings = briefingRepository.findAllByTypeAndCreatedAtBetween(
        briefingType, startDateTime, endDateTime);

    return BriefingsResponse.from(date, briefings);
  }

  public BriefingDetailResponse findBriefing(final Long id) {
    final Briefing briefing = briefingRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 브리핑입니다."));

    return BriefingDetailResponse.from(briefing);
  }
}
