package briefing.briefing.api;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import briefing.briefing.application.BriefingFacade;
import briefing.briefing.application.dto.*;
import briefing.common.aop.annotation.CacheEvictByBriefingId;
import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
import briefing.security.handler.annotation.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "03-Briefing \uD83D\uDCF0", description = "브리핑 관련 API")
@RestController
@RequiredArgsConstructor
public class BriefingApi {

    private final BriefingFacade briefingFacade;

    @GetMapping("/briefings")
    @Parameter(name = "timeOfDay", hidden = true)
    @Operation(summary = "03-01Briefing \uD83D\uDCF0  브리핑 목록 조회 V1", description = "")
    public CommonResponse<BriefingResponseDTO.BriefingPreviewListDTO> findBriefings(
            @ParameterObject @ModelAttribute BriefingRequestParam.BriefingPreviewListParam params) {
        return CommonResponse.onSuccess(briefingFacade.findBriefings(params));
    }

    @GetMapping("/briefings/{id}")
    @Parameter(name = "member", hidden = true)
    @Operation(summary = "03-02Briefing \uD83D\uDCF0  브리핑 단건 조회 V1", description = "")
    public CommonResponse<BriefingResponseDTO.BriefingDetailDTO> findBriefing(
            @PathVariable final Long id, @AuthMember Member member) {
        return CommonResponse.onSuccess(briefingFacade.findBriefing(id, member));
    }

    @CacheEvict(value = "findBriefingsV2", key = "#request.getBriefingType()")
    @PostMapping("/briefings")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "03-03Briefing \uD83D\uDCF0  브리핑 등록", description = "")
    public void createBriefing(@RequestBody final BriefingRequestDTO.BriefingCreate request) {
        briefingFacade.createBriefing(request);
    }

    /*
     * TODO 브리핑 수정 API는 우선적으로 인가 처리를 진행하지 않으나
     *  빠른 시일 내로 브리핑 등록과 함께 인가 처리 예정
     *  즉 유저에게 권한을 부여하는 일련의 과정에 대한 리팩토링이 필요함 이는 CYY1007이 진행하겠음
     */

    /**
     * @param id, BriefingResponseDTO.BriefingUpdateDTO
     * @return 수정된 값, 요청으로 온 값과 동일
     */
    @CacheEvictByBriefingId(value = "findBriefingsV2", briefingId = "#id")
    @Operation(summary = "03-04Briefing \uD83D\uDCF0  브리핑 내용 수정", description = "")
    @Parameter(name = "id", description = "브리핑 아이디", example = "1")
    @PatchMapping("/briefings/{id}")
    public CommonResponse<BriefingResponseDTO.BriefingUpdateDTO> patchBriefingContent(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid BriefingRequestDTO.BriefingUpdateDTO request) {
        return CommonResponse.onSuccess(briefingFacade.updateBriefing(id, request));
    }
}
