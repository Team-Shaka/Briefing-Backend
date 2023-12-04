package briefing.scrap.api;

import briefing.briefing.domain.Briefing;
import briefing.member.domain.Member;
import briefing.scrap.application.dto.ScrapResponse;
import briefing.scrap.domain.Scrap;

import java.time.LocalDateTime;

public class ScrapConverter {
    public static ScrapResponse.CreateDTO toCreateDTO(Scrap createdScrap) {
        return ScrapResponse.CreateDTO.builder()
                .scrapId(createdScrap.getId())
                .memberId(createdScrap.getMember().getId())
                .briefingId(createdScrap.getBriefing().getId())
                .createdAt(createdScrap.getCreatedAt())
                .build();
    }

    public static Scrap toScrap(Member member, Briefing briefing) {
        return Scrap.builder()
                .member(member)
                .briefing(briefing)
                .build();
    }

    public static ScrapResponse.DeleteDTO toDeleteDTO(Scrap deletedScrap) {
        return ScrapResponse.DeleteDTO.builder()
                .scrapId(deletedScrap.getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static ScrapResponse.ReadDTO toReadDTO(Scrap scrap) {
        return ScrapResponse.ReadDTO.builder()
                .briefingId(scrap.getBriefing().getId())
                .ranks(scrap.getBriefing().getRanks())
                .title(scrap.getBriefing().getTitle())
                .subtitle(scrap.getBriefing().getSubtitle())
                .date(scrap.getBriefing().getCreatedAt().toLocalDate())
                .gptModel(scrap.getBriefing().getGptModel())
                .timeOfDay(scrap.getBriefing().getTimeOfDay())
                .build();
    }
}
