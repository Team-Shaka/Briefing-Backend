package briefing.briefing.application.strategy;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class BriefingV2QueryStrategy implements BriefingQueryStrategy{

    private final BriefingRepository briefingRepository;

    // TODO - V2에 맞게 쿼리 변경하기
    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        final LocalDateTime startDateTime = params.getDate().atStartOfDay();
        final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

        return briefingRepository.findBriefingsWithScrapCount(
                params.getType(), startDateTime, endDateTime);
    }
}
