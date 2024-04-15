package com.example.briefingapi.scrap.business;

import java.util.List;

import com.example.briefingapi.briefing.implement.service.BriefingQueryAdapter;
import com.example.briefingapi.member.implement.MemberQueryAdapter;
import com.example.briefingapi.scrap.implement.ScrapCommandAdapter;
import com.example.briefingapi.scrap.implement.ScrapQueryAdapter;
import com.example.briefingapi.scrap.presentation.dto.ScrapRequest;
import com.example.briefingapi.scrap.presentation.dto.ScrapResponse;
import com.example.briefingcommon.common.exception.common.ErrorCode;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.Scrap;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.briefingcommon.common.exception.ScrapException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapQueryAdapter scrapQueryAdapter;
    private final ScrapCommandAdapter scrapCommandAdapter;
    private final MemberQueryAdapter memberQueryAdapter;
    private final BriefingQueryAdapter briefingQueryAdapter;

    @Transactional
    public ScrapResponse.CreateDTO create(final ScrapRequest.CreateDTO request) {
        if (Boolean.TRUE.equals(
                scrapQueryAdapter.existsByMemberIdAndBriefingId(
                        request.getMemberId(), request.getBriefingId())))
            throw new ScrapException(ErrorCode.SCRAP_ALREADY_EXISTS);

        Member member = memberQueryAdapter.findById(request.getMemberId());
        Briefing briefing =
                briefingQueryAdapter.findBriefing(request.getBriefingId(), APIVersion.V1);

        Scrap scrap = ScrapMapper.toScrap(member, briefing);
        Scrap createdScrap = scrapCommandAdapter.create(scrap);
        return ScrapMapper.toCreateDTO(createdScrap);
    }

    @Transactional
    public ScrapResponse.DeleteDTO delete(final Long briefingId, final Long memberId) {
        Scrap scrap = scrapQueryAdapter.getScrapByBriefingIdAndMemberId(briefingId, memberId);
        Scrap deletedScrap = scrapCommandAdapter.delete(scrap);
        return ScrapMapper.toDeleteDTO(deletedScrap);
    }

    @Transactional(readOnly = true)
    public List<ScrapResponse.ReadDTO> getScrapsByMemberId(final Long memberId) {
        List<Scrap> scraps = scrapQueryAdapter.getScrapsByMemberId(memberId);
        return scraps.stream().map(ScrapMapper::toReadDTO).toList();
    }
}
