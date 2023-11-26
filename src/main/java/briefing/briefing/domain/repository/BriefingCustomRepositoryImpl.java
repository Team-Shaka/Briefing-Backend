package briefing.briefing.domain.repository;

import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import briefing.briefing.domain.QBriefing;
import briefing.briefing.domain.TimeOfDay;
import briefing.scrap.domain.QScrap;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BriefingCustomRepositoryImpl implements BriefingCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Briefing> findBriefingsWithScrapCount(BriefingType type, LocalDateTime start, LocalDateTime end, TimeOfDay timeOfDay) {
        QBriefing briefing = QBriefing.briefing;
        QScrap scrap = QScrap.scrap;

        // 쿼리 결과를 Tuple로 가져옵니다.
        List<Tuple> results = queryFactory.select(briefing, scrap.count())
                .from(briefing)
                .leftJoin(scrap).on(scrap.briefing.eq(briefing))
                .where(briefing.type.eq(type)
                        .and(briefing.createdAt.between(start, end))
                        .and(briefing.timeOfDay.eq(timeOfDay))
                )
                .groupBy(briefing)
                .orderBy(briefing.ranks.asc())
                .fetch();

        // Tuple 결과를 Briefing과 scrapCount로 변환합니다.
        return results.stream()
                .map(tuple -> {
                    Briefing b = tuple.get(briefing);
                    b.setScrapCount(Math.toIntExact(tuple.get(scrap.count())));
                    return b;
                })
                .toList();
    }
}
