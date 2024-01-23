package com.example.briefingapi.briefing.implement.strategy;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.example.briefingcommon.domain.repository.article.BriefingRepository;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;


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
