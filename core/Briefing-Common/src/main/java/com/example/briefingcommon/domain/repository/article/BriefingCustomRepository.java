package com.example.briefingcommon.domain.repository.article;

import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.BriefingType;
import com.example.briefingcommon.entity.enums.TimeOfDay;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BriefingCustomRepository {
    List<Briefing> findBriefingsWithScrapCount(
            BriefingType type, LocalDateTime start, LocalDateTime end, TimeOfDay timeOfDay);

    List<Briefing> findTop10ByTypeOrderByCreatedAtDesc(BriefingType type);

    Optional<Briefing> findByIdWithScrapCount(Long id);
}
