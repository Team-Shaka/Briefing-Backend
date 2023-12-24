package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.repository.BriefingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BriefingV1QueryStrategy implements BriefingQueryStrategy {

    private final BriefingRepository briefingRepository;

    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        final LocalDateTime startDateTime = params.getDate().atStartOfDay();
        final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

        List<Briefing> briefingList = briefingRepository.findAllByTypeAndCreatedAtBetweenOrderByRanks(params.getType(), startDateTime, endDateTime);
        if(briefingList.isEmpty()) {
            briefingList = briefingRepository.findTop10ByTypeOrderByCreatedAtDesc(BriefingType.SOCIAL);
            Collections.reverse(briefingList);
        }
        return briefingList;
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findById(id);
    }
}
