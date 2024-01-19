package briefing.scrap.business;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.domain.Briefing;
import briefing.briefing.implement.service.BriefingQueryService;
import briefing.common.enums.APIVersion;
import briefing.common.exception.common.ErrorCode;
import briefing.member.domain.Member;
import briefing.member.implement.MemberQueryService;
import briefing.scrap.domain.Scrap;
import briefing.scrap.exception.ScrapException;
import briefing.scrap.implement.ScrapCommandService;
import briefing.scrap.implement.ScrapQueryService;
import briefing.scrap.presentation.dto.ScrapRequest;
import briefing.scrap.presentation.dto.ScrapResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapFacade {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;
    private final MemberQueryService memberQueryService;
    private final BriefingQueryService briefingQueryService;

    @Transactional
    public ScrapResponse.CreateDTO create(final ScrapRequest.CreateDTO request) {
        if (Boolean.TRUE.equals(
                scrapQueryService.existsByMemberIdAndBriefingId(
                        request.getMemberId(), request.getBriefingId())))
            throw new ScrapException(ErrorCode.SCRAP_ALREADY_EXISTS);

        Member member = memberQueryService.findById(request.getMemberId());
        Briefing briefing =
                briefingQueryService.findBriefing(request.getBriefingId(), APIVersion.V1);

        Scrap scrap = ScrapConverter.toScrap(member, briefing);
        Scrap createdScrap = scrapCommandService.create(scrap);
        return ScrapConverter.toCreateDTO(createdScrap);
    }

    @Transactional
    public ScrapResponse.DeleteDTO delete(final Long briefingId, final Long memberId) {
        Scrap scrap = scrapQueryService.getScrapByBriefingIdAndMemberId(briefingId, memberId);
        Scrap deletedScrap = scrapCommandService.delete(scrap);
        return ScrapConverter.toDeleteDTO(deletedScrap);
    }

    @Transactional(readOnly = true)
    public List<ScrapResponse.ReadDTO> getScrapsByMemberId(final Long memberId) {
        List<Scrap> scraps = scrapQueryService.getScrapsByMemberId(memberId);
        return scraps.stream().map(ScrapConverter::toReadDTO).toList();
    }
}
