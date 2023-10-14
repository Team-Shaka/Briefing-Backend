package briefing.briefing.api;

import briefing.briefing.application.BriefingCommandService;
import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.*;
import briefing.briefing.domain.BriefingType;
import java.time.LocalDate;

import briefing.common.response.CommonResponse;
import briefing.member.domain.Member;
import briefing.security.handler.annotation.AuthMember;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

  @GetMapping
  public CommonResponse<BriefingResponseDTO.BriefingPreviewListDTO> findBriefings(
      @RequestParam("type") final BriefingType type,
      @RequestParam("date") final LocalDate date
  ) {
    return CommonResponse.onSuccess(BriefingConverter.toBriefingPreviewListDTO(date, briefingQueryService.findBriefings(type, date)));
  }

  @GetMapping("/{id}")
  @Parameter(name = "member", hidden = true)
  public CommonResponse<BriefingResponseDTO.BriefingDetailDTO> findBriefing(@PathVariable final Long id, @AuthMember Member member) {
    System.out.println("Briefing Detail Member" + member);
    return CommonResponse.onSuccess(BriefingConverter.toBriefingDetailDTO(briefingQueryService.findBriefing(id)));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createBriefing(@RequestBody final BriefingRequestDTO.BriefingCreate request) {
    briefingCommandService.createBriefing(request);
  }
}
