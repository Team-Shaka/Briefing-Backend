package briefing.scrap.business;

import java.time.LocalDateTime;

import briefing.briefing.domain.Briefing;
import briefing.member.domain.Member;
import briefing.scrap.domain.Scrap;
import briefing.scrap.presentation.dto.ScrapResponse;

public class ScrapConverter {
    public static ScrapResponse.CreateDTO toCreateDTO(Scrap createdScrap) {
        return ScrapResponse.CreateDTO.builder()
                .scrapId(createdScrap.getId())
                .memberId(createdScrap.getMember().getId())
                .briefingId(createdScrap.getBriefing().getId())
                .createdAt(createdScrap.getCreatedAt())
                .build();
    }

    public static ScrapResponse.CreateDTOV2 toCreateDTOV2(Scrap createdScrap, Integer scrapCount) {
        return ScrapResponse.CreateDTOV2.builder()
                .scrapId(createdScrap.getId())
                .memberId(createdScrap.getMember().getId())
                .briefingId(createdScrap.getBriefing().getId())
                .scrapCount(scrapCount)
                .isScrap(Boolean.TRUE)
                .createdAt(createdScrap.getCreatedAt())
                .build();
    }

    public static Scrap toScrap(Member member, Briefing briefing) {
        return Scrap.builder().member(member).briefing(briefing).build();
    }

    public static ScrapResponse.DeleteDTO toDeleteDTO(Scrap deletedScrap) {
        return ScrapResponse.DeleteDTO.builder()
                .scrapId(deletedScrap.getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }

    public static ScrapResponse.DeleteDTOV2 toDeleteDTOV2(Scrap deletedScrap, Integer scrapCount) {
        return ScrapResponse.DeleteDTOV2.builder()
                .scrapId(deletedScrap.getId())
                .isScrap(Boolean.FALSE)
                .scrapCount(scrapCount)
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
                .build();
    }

    public static ScrapResponse.ReadDTOV2 toReadDTOV2(Scrap scrap) {
        return ScrapResponse.ReadDTOV2.builder()
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
