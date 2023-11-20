package briefing.briefing.api;

import briefing.briefing.application.BriefingCommandService;
import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.*;
import briefing.briefing.domain.BriefingType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
import briefing.scrap.application.ScrapQueryService;
import briefing.security.handler.annotation.AuthMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
      @RequestParam("type") final BriefingType type,
      @RequestParam("date") final LocalDate date
  ) {
    return CommonResponse.onSuccess(BriefingConverter.toBriefingPreviewListDTO(date, briefingQueryService.findBriefings(type, date)));
  }

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
  public CommonResponse<BriefingResponseDTO.BriefingDetailDTO> findBriefing(@PathVariable final Long id, @AuthMember Member member) {

    Boolean isScrap = Optional.ofNullable(member)
            .map(m -> scrapQueryService.existsByMemberIdAndBriefingId(m.getId(), id))
            .orElseGet(() -> Boolean.FALSE);

    /*
      TODO
      업데이트가 확정되면 로직을 거쳐 isBriefingOpen과 isWarning을 세팅해주어야합니다.
     */
    Boolean isBriefingOpen = false;
    Boolean isWarning = false;

    return CommonResponse.onSuccess(BriefingConverter.toBriefingDetailDTO(briefingQueryService.findBriefing(id), isScrap, isBriefingOpen, isWarning));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createBriefing(@RequestBody final BriefingRequestDTO.BriefingCreate request) {
    briefingCommandService.createBriefing(request);
  }
}
