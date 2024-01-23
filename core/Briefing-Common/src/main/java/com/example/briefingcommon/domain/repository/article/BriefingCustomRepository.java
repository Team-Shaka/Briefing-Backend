package com.example.briefingcommon.domain.repository.article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.BriefingType;
import com.example.briefingcommon.entity.enums.TimeOfDay;
import org.springframework.stereotype.Repository;


public interface BriefingCustomRepository {
    List<Briefing> findBriefingsWithScrapCount(
            BriefingType type, LocalDateTime start, LocalDateTime end, TimeOfDay timeOfDay);

    List<Briefing> findTop10ByTypeOrderByCreatedAtDesc(BriefingType type);

    Optional<Briefing> findByIdWithScrapCount(Long id);
}
