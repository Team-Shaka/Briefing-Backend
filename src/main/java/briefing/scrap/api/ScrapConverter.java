package briefing.scrap.api;

import briefing.briefing.domain.Briefing;
import briefing.member.domain.Member;
import briefing.scrap.application.dto.ScrapResponse;
import briefing.scrap.domain.Scrap;

public class ScrapConverter {
    public static ScrapResponse.CreateDTO toCreateDTO(Scrap scrap) {
        return ScrapResponse.CreateDTO.builder()
                .scrapId(scrap.getId())
                .memberId(scrap.getMember().getId())
                .briefingId(scrap.getBriefing().getId())
                .createdAt(scrap.getCreatedAt())
                .build();
    }

    public static Scrap toScrap(Member member, Briefing briefing) {
        return Scrap.builder()
                .member(member)
                .briefing(briefing)
                .build();
    }
}
