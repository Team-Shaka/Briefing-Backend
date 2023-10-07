package briefing.scrap.domain.repository;

import briefing.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByBriefing_IdAndMember_Id(Long briefingId, Long memberId);

    boolean existsByMember_IdAndBriefing_Id(Long memberId, Long briefingId);

    List<Scrap> findByMember_Id(Long memberId);
}
