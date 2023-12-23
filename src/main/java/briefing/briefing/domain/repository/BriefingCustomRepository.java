package briefing.briefing.domain.repository;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BriefingCustomRepository {
    List<Briefing> findBriefingsWithScrapCount(BriefingType type, LocalDateTime start, LocalDateTime end, TimeOfDay timeOfDay);

    List<Briefing> findTop10ByTypeOrderByCreatedAtDesc(BriefingType type);

    Optional<Briefing> findByIdWithScrapCount(Long id);
}
