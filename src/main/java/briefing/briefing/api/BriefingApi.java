package briefing.briefing.api;

import briefing.briefing.application.BriefingCommandService;
import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.*;
import briefing.briefing.domain.Briefing;
import briefing.briefing.domain.BriefingType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import briefing.briefing.domain.TimeOfDay;
import briefing.common.enums.APIVersion;
import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
import briefing.scrap.application.ScrapQueryService;
import briefing.security.handler.annotation.AuthMember;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "03-Briefing \uD83D\uDCF0",description = "브리핑 관련 API")
@RestController
@RequestMapping("/briefings")
@RequiredArgsConstructor
public class BriefingApi {

  private final BriefingQueryService briefingQueryService;
  private final BriefingCommandService briefingCommandService;
  private final ScrapQueryService scrapQueryService;

  @GetMapping
  public CommonResponse<BriefingResponseDTO.BriefingPreviewListDTO> findBriefings(
      @ParameterObject @ModelAttribute BriefingRequestParam.BriefingPreviewListParam params
  ) {

    List<Briefing> briefingList = briefingQueryService.findBriefings(params);
    return CommonResponse.onSuccess(BriefingConverter.toBriefingPreviewListDTO(params.getDate(), briefingList));
  }

  @Deprecated
  @Operation(summary = "키워드 전달 V2 임시 API", description = "키워드 전달 V2 임시 API 입니다. 응답은 무조건 동일합니다. type만 주신걸 담아서 드립니다.")
  @ApiResponse(responseCode = "1000", description = "OK, 성공")
  @GetMapping("/temp")
  public CommonResponse<BriefingResponseDTO.BriefingV2PreviewListDTO> findBriefingsV2Temp(
          @RequestParam("type") final BriefingType type,
          @RequestParam("date") final LocalDate date
  ){
    List<Long> idList = Arrays.asList(346L, 347L, 348L, 349L, 350L);
    return CommonResponse.onSuccess(BriefingConverter.toBriefingPreviewV2TempListDTO(date,idList,type));
  }

  @GetMapping("/{id}")
  @Parameter(name = "member", hidden = true)
  public CommonResponse<BriefingResponseDTO.BriefingDetailDTO> findBriefing(
          @PathVariable final Long id,
          @RequestParam(value = "version", required = false, defaultValue = "1.1.0") final APIVersion version,
          @AuthMember Member member
  ) {

    Boolean isScrap = Optional.ofNullable(member)
            .map(m -> scrapQueryService.existsByMemberIdAndBriefingId(m.getId(), id))
            .orElseGet(() -> Boolean.FALSE);

    Boolean isBriefingOpen = false;
    Boolean isWarning = false;

    return CommonResponse.onSuccess(BriefingConverter.toBriefingDetailDTO(briefingQueryService.findBriefing(id, version), isScrap, isBriefingOpen, isWarning));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createBriefing(@RequestBody final BriefingRequestDTO.BriefingCreate request) {
    briefingCommandService.createBriefing(request);
  }
}
