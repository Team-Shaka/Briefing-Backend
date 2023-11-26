package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BriefingV1QueryStrategy implements BriefingQueryStrategy {

    private final BriefingRepository briefingRepository;

    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        final LocalDateTime startDateTime = params.getDate().atStartOfDay();
        final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

        return briefingRepository.findAllByTypeAndCreatedAtBetweenOrderByRanks(
                params.getType(), startDateTime, endDateTime);
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findById(id);
    }
}
