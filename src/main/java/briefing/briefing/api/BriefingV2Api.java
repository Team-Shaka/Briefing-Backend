package briefing.briefing.api;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import briefing.briefing.application.BriefingV2Facade;
import briefing.briefing.application.dto.BriefingRequestParam;
import briefing.briefing.application.dto.BriefingResponseDTO;
import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
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
    private final BriefingV2Facade briefingFacade;

    @GetMapping("/briefings")
    @Operation(summary = "03-01Briefing \uD83D\uDCF0  브리핑 목록 조회 V2", description = "")
    @Cacheable(value = "findBriefingsV2", key = "#params.getType()")
    public CommonResponse<BriefingResponseDTO.BriefingPreviewListDTOV2> findBriefingsV2(
            @ParameterObject @ModelAttribute BriefingRequestParam.BriefingPreviewListParam params) {
        return CommonResponse.onSuccess(briefingFacade.findBriefings(params));
    }

    @GetMapping("/briefings/{id}")
    @Operation(summary = "03-02Briefing \uD83D\uDCF0  브리핑 단건 조회 V2", description = "")
    @Parameter(name = "member", hidden = true)
    public CommonResponse<BriefingResponseDTO.BriefingDetailDTOV2> findBriefingV2(
            @PathVariable final Long id, @AuthMember Member member) {
        return CommonResponse.onSuccess(briefingFacade.findBriefing(id, member));
    }
}
