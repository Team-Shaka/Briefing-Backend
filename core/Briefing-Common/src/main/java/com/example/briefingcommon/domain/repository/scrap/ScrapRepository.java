package com.example.briefingcommon.domain.repository.scrap;


import com.example.briefingcommon.entity.Scrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapCustomRepository {

    Optional<Scrap> findByBriefing_IdAndMember_Id(Long briefingId, Long memberId);

    boolean existsByMember_IdAndBriefing_Id(Long memberId, Long briefingId);

    List<Scrap> findByMember_Id(Long memberId);

    Integer countByBriefing_Id(Long briefingId);
}
