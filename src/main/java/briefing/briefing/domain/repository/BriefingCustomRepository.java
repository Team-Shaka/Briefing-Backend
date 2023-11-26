package briefing.briefing.domain.repository;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.TimeOfDay;

import java.time.LocalDateTime;
import java.util.List;

public interface BriefingCustomRepository {
    List<Briefing> findBriefingsWithScrapCount(BriefingType type, LocalDateTime start, LocalDateTime end, TimeOfDay timeOfDay);
}
