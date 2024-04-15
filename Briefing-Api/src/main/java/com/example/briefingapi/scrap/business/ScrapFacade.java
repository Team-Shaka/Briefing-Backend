package com.example.briefingapi.scrap.business;

import java.util.List;

import com.example.briefingapi.briefing.implement.service.BriefingQueryAdapter;
import com.example.briefingapi.member.implement.MemberQueryAdapter;
import com.example.briefingapi.scrap.implement.ScrapCommandService;
import com.example.briefingapi.scrap.implement.ScrapQueryService;
import com.example.briefingapi.scrap.presentation.dto.ScrapRequest;
import com.example.briefingapi.scrap.presentation.dto.ScrapResponse;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Scrap;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import com.example.briefingcommon.common.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScrapFacade {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;
    private final MemberQueryAdapter memberQueryAdapter;
    private final BriefingQueryAdapter briefingQueryAdapter;

    @Transactional
    public ScrapResponse.CreateDTO create(final ScrapRequest.CreateDTO request) {
        if (Boolean.TRUE.equals(
                scrapQueryService.existsByMemberIdAndBriefingId(
                        request.getMemberId(), request.getBriefingId())))
            throw new ScrapException(ErrorCode.SCRAP_ALREADY_EXISTS);

        Member member = memberQueryAdapter.findById(request.getMemberId());
        Briefing briefing =
                briefingQueryAdapter.findBriefing(request.getBriefingId(), APIVersion.V1);

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
