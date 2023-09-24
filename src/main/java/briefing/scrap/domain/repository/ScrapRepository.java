package briefing.scrap.domain.repository;

import briefing.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    boolean existsByMember_IdAndBriefing_Id(Long memberId, Long briefingId);
}
