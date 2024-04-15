package com.example.briefingapi.briefing.business;

import java.util.List;
import java.util.Optional;

import com.example.briefingapi.briefing.implement.service.BriefingCommandAdapter;
import com.example.briefingapi.briefing.implement.service.BriefingQueryAdapter;
import com.example.briefingapi.briefing.presentation.dto.BriefingRequestParam;
import com.example.briefingapi.briefing.presentation.dto.BriefingResponseDTO;
import com.example.briefingapi.scrap.implement.ScrapQueryAdapter;
import com.example.briefingcommon.entity.Briefing;
import com.example.briefingcommon.entity.Member;
import com.example.briefingcommon.entity.enums.APIVersion;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BriefingV2Service {

    private final BriefingQueryAdapter briefingQueryAdapter;
    private final BriefingCommandAdapter briefingCommandAdapter;
    private final ScrapQueryAdapter scrapQueryAdapter;
    private static final APIVersion version = APIVersion.V2;

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingPreviewListDTOV2 findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryAdapter.findBriefings(params, version);
        return BriefingMapper.toBriefingPreviewListDTOV2(params.getDate(), briefingList);
    }

    @Transactional
    public BriefingResponseDTO.BriefingDetailDTOV2 findBriefing(final Long id, Member member) {
        briefingCommandAdapter.increaseViewCountById(id);
        Boolean isScrap =
                Optional.ofNullable(member)
                        .map(m -> scrapQueryAdapter.existsByMemberIdAndBriefingId(m.getId(), id))
                        .orElseGet(() -> Boolean.FALSE);

        Boolean isBriefingOpen = false;
        Boolean isWarning = false;

        return BriefingMapper.toBriefingDetailDTOV2(
                briefingQueryAdapter.findBriefing(id, version), isScrap, isBriefingOpen, isWarning);
    }
}
