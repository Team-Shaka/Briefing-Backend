package briefing.briefing.business;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import briefing.briefing.domain.Briefing;
import briefing.briefing.implement.service.BriefingQueryService;
import briefing.briefing.presentation.dto.BriefingRequestParam;
import briefing.briefing.presentation.dto.BriefingResponseDTO;
import briefing.common.enums.APIVersion;
import briefing.member.domain.Member;
import briefing.scrap.implement.ScrapQueryService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BriefingV2Facade {

    private final BriefingQueryService briefingQueryService;
    private final ScrapQueryService scrapQueryService;
    private static final APIVersion version = APIVersion.V2;

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingPreviewListDTOV2 findBriefings(
            BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryService.findBriefings(params, version);
        return BriefingConverter.toBriefingPreviewListDTOV2(params.getDate(), briefingList);
    }

    @Transactional(readOnly = true)
    public BriefingResponseDTO.BriefingDetailDTOV2 findBriefing(final Long id, Member member) {
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
