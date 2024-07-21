package com.example.briefingapi.briefing.implement.strategy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.APIVersion;
import com.example.briefingcommon.entity.enums.BriefingType;
import org.springframework.stereotype.Component;



import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BriefingV1QueryStrategy implements BriefingQueryStrategy {

    private final BriefingRepository briefingRepository;

    @Override
    public List<Briefing> findBriefings(BriefingRequestParam.BriefingPreviewListParam params) {
        final LocalDateTime startDateTime = params.getDate().atStartOfDay();
        final LocalDateTime endDateTime = params.getDate().atTime(LocalTime.MAX);

        List<Briefing> briefingList =
                briefingRepository.findAllByTypeAndCreatedAtBetweenOrderByRanks(
                        params.getType(), startDateTime, endDateTime);
        if (briefingList.isEmpty()) {
            briefingList = briefingRepository.findTop10ByTypeOrderByCreatedAtDesc(BriefingType.SOCIAL);
        }
        return briefingList;
    }

    @Override
    public Optional<Briefing> findById(Long id) {
        return briefingRepository.findById(id);
    }

    @Override
    public APIVersion getVersion() {
        return APIVersion.V1;
    }
}
