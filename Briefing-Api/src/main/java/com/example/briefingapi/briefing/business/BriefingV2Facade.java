package com.example.briefingapi.briefing.business;

import java.util.List;
import java.util.Optional;

import com.example.briefingapi.briefing.implement.service.BriefingCommandService;
import com.example.briefingapi.briefing.implement.service.BriefingQueryService;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingapi.briefing.presentation.dto.BriefingResponseDTO;
import com.example.briefingapi.scrap.implement.ScrapQueryService;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BriefingV2Facade {

    private final BriefingQueryService briefingQueryService;
    private final BriefingCommandService briefingCommandService;
    private final ScrapQueryService scrapQueryService;
    private static final APIVersion version = APIVersion.V2;

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingPreviewListDTOV2 findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryService.findBriefings(params, version);
        return BriefingConverter.toBriefingPreviewListDTOV2(params.getDate(), briefingList);
    }

    @Transactional
    public BriefingResponseDTO.BriefingDetailDTOV2 findBriefing(final Long id, Member member) {
        briefingCommandService.increaseViewCountById(id);
        Boolean isScrap =
                Optional.ofNullable(member)
                        .map(m -> scrapQueryService.existsByMemberIdAndBriefingId(m.getId(), id))
                        .orElseGet(() -> Boolean.FALSE);

        Boolean isBriefingOpen = false;
        Boolean isWarning = false;

        return BriefingConverter.toBriefingDetailDTOV2(
                briefingQueryService.findBriefing(id, version), isScrap, isBriefingOpen, isWarning);
    }
}
