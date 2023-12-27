package briefing.scrap.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import briefing.scrap.domain.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByBriefing_IdAndMember_Id(Long briefingId, Long memberId);

    boolean existsByMember_IdAndBriefing_Id(Long memberId, Long briefingId);

    List<Scrap> findByMember_Id(Long memberId);
}
