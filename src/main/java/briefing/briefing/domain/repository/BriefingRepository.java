package briefing.briefing.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;

@Repository
public interface BriefingRepository
        extends JpaRepository<Briefing, Long>, BriefingCustomRepository {

    List<Briefing> findAllByTypeAndCreatedAtBetweenOrderByRanks(
            BriefingType type, LocalDateTime start, LocalDateTime end);
}
