package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BriefingV2QueryStrategy implements BriefingQueryStrategy{

    private final BriefingRepository briefingRepository;

    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        if(params.isPresentDate()) {
            final LocalDateTime startDateTime = params.getDate().atStartOfDay();
            final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

            return briefingRepository.findBriefingsWithScrapCount(
                    params.getType(), startDateTime, endDateTime, params.getTimeOfDay());
        }

        List<Briefing> briefingList = briefingRepository.findTop10ByTypeOrderByCreatedAtDesc(params.getType());
        Collections.reverse(briefingList);
        return briefingList;
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findByIdWithScrapCount(id);
    }
}
