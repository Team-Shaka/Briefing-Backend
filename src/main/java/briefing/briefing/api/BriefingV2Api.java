package briefing.briefing.api;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import briefing.briefing.application.BriefingCommandService;
import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.application.dto.BriefingResponseDTO;
import briefing.briefing.domain.Briefing;
import briefing.common.enums.APIVersion;
import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
import briefing.scrap.application.ScrapQueryService;
import briefing.security.handler.annotation.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "03-Briefing V2 \uD83D\uDCF0", description = "브리핑 관련 API V2")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class BriefingV2Api {
    private final BriefingQueryService briefingQueryService;
    private final BriefingCommandService briefingCommandService;
    private final ScrapQueryService scrapQueryService;

    @GetMapping("/briefings")
    @Operation(summary = "03-01Briefing \uD83D\uDCF0  브리핑 목록 조회 V2", description = "")
    public CommonResponse<BriefingResponseDTO.BriefingPreviewListDTOV2> findBriefingsV2(
            @ParameterObject @ModelAttribute BriefingRequestParam.BriefingPreviewListParam params) {
        List<Briefing> briefingList = briefingQueryService.findBriefings(params, APIVersion.V2);
        return CommonResponse.onSuccess(
                BriefingConverter.toBriefingPreviewListDTOV2(params.getDate(), briefingList));
    }

    @GetMapping("/briefings/{id}")
    @Operation(summary = "03-02Briefing \uD83D\uDCF0  브리핑 단건 조회 V2", description = "")
    @Parameter(name = "member", hidden = true)
    public CommonResponse<BriefingResponseDTO.BriefingDetailDTOV2> findBriefingV2(
            @PathVariable final Long id, @AuthMember Member member) {

        Boolean isScrap =
                Optional.ofNullable(member)
                        .map(m -> scrapQueryService.existsByMemberIdAndBriefingId(m.getId(), id))
                        .orElseGet(() -> Boolean.FALSE);

        Boolean isBriefingOpen = false;
        Boolean isWarning = false;

        return CommonResponse.onSuccess(
                BriefingConverter.toBriefingDetailDTOV2(
                        briefingQueryService.findBriefing(id, APIVersion.V2),
                        isScrap,
                        isBriefingOpen,
                        isWarning));
    }
}
