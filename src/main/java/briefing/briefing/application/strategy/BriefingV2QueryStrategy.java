package briefing.briefing.application.strategy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.repository.BriefingRepository;
import briefing.common.enums.APIVersion;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BriefingV2QueryStrategy implements BriefingQueryStrategy {

    private final BriefingRepository briefingRepository;

    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList;
        if (params.isPresentDate()) {
            final LocalDateTime startDateTime = params.getDate().atStartOfDay();
            final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

            briefingList =
                    briefingRepository.findBriefingsWithScrapCount(
                            params.getType(), startDateTime, endDateTime, params.getTimeOfDay());
            if (!briefingList.isEmpty()) return briefingList;
        }

        briefingList = briefingRepository.findTop10ByTypeOrderByCreatedAtDesc(params.getType());
        return briefingList;
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findByIdWithScrapCount(id);
    }

    @Override
    public APIVersion getVersion() {
        return APIVersion.V2;
    }
}
