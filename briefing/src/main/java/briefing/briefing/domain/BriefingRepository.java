package briefing.briefing.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingRepository extends JpaRepository<Briefing, Long> {

  List<Briefing> findAllByTypeAndCreatedAtBetween(BriefingType type, LocalDateTime start,
      LocalDateTime end);
}
