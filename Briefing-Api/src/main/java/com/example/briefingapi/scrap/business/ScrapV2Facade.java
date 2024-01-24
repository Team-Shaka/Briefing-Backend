package com.example.briefingapi.scrap.business;

import java.util.List;

import com.example.briefingapi.briefing.implement.service.BriefingQueryService;
import com.example.briefingapi.member.implement.MemberQueryService;
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
public class ScrapV2Facade {
    private final ScrapQueryService scrapQueryService;
    private final ScrapCommandService scrapCommandService;
    private final MemberQueryService memberQueryService;
    private final BriefingQueryService briefingQueryService;

    @Transactional
    public ScrapResponse.CreateDTOV2 create(final ScrapRequest.CreateDTO request) {
        if (Boolean.TRUE.equals(
                scrapQueryService.existsByMemberIdAndBriefingId(
                        request.getMemberId(), request.getBriefingId())))
            throw new ScrapException(ErrorCode.SCRAP_ALREADY_EXISTS);

        Member member = memberQueryService.findById(request.getMemberId());
        Briefing briefing =
                briefingQueryService.findBriefing(request.getBriefingId(), APIVersion.V2);

        Scrap scrap = ScrapConverter.toScrap(member, briefing);
        Scrap createdScrap = scrapCommandService.create(scrap);

        Integer scrapCount = scrapQueryService.countByBriefingId(request.getBriefingId());

        return ScrapConverter.toCreateDTOV2(createdScrap, scrapCount);
    }

    @Transactional
    public ScrapResponse.DeleteDTOV2 delete(final Long briefingId, final Long memberId) {
        Scrap scrap = scrapQueryService.getScrapByBriefingIdAndMemberId(briefingId, memberId);
        Scrap deletedScrap = scrapCommandService.delete(scrap);
        Integer scrapCount = scrapQueryService.countByBriefingId(briefingId);
        return ScrapConverter.toDeleteDTOV2(deletedScrap, scrapCount);
    }

    @Transactional(readOnly = true)
    public List<ScrapResponse.ReadDTOV2> getScrapsByMemberId(final Long memberId) {
        List<Scrap> scraps = scrapQueryService.getScrapsByMemberId(memberId);
        return scraps.stream().map(ScrapConverter::toReadDTOV2).toList();
    }
}
