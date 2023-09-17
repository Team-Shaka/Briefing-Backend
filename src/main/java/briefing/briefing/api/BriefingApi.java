package briefing.briefing.api;

import briefing.briefing.application.BriefingCommandService;
import briefing.briefing.application.BriefingQueryService;
import briefing.briefing.application.dto.BriefingCreateRequest;
import briefing.briefing.application.dto.BriefingDetailResponse;
import briefing.briefing.application.dto.BriefingsResponse;
import briefing.briefing.domain.BriefingType;
import java.time.LocalDate;

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
  public BriefingsResponse findBriefings(
      @RequestParam("type") final BriefingType type,
      @RequestParam("date") final LocalDate date
  ) {
    return briefingQueryService.findBriefings(type, date);
  }

  @GetMapping("/{id}")
  public BriefingDetailResponse findBriefing(@PathVariable final Long id) {
    return briefingQueryService.findBriefing(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createBriefing(@RequestBody final BriefingCreateRequest request) {
    briefingCommandService.createBriefing(request);
  }
}
