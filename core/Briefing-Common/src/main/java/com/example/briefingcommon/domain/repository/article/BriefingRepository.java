package com.example.briefingcommon.domain.repository.article;

import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.enums.BriefingType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BriefingRepository
        extends JpaRepository<Briefing, Long>, BriefingCustomRepository {

    List<Briefing> findAllByTypeAndCreatedAtBetweenOrderByRanks(
            BriefingType type, LocalDateTime start, LocalDateTime end);

    @Query("SELECT b from Briefing b where b.ranks = 1 and b.type = :type order by b.createdAt desc")
    Page<Briefing> getBestTodayBriefing(@Param("type") BriefingType type, Pageable pageable);
}
